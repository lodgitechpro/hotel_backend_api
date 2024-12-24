package com.lodgitechpro.hotelmanagement.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lodgitechpro.hotelmanagement.auth.token.Token;
import com.lodgitechpro.hotelmanagement.auth.token.TokenRepository;
import com.lodgitechpro.hotelmanagement.auth.token.TokenType;
import com.lodgitechpro.hotelmanagement.entity.Employee;
import com.lodgitechpro.hotelmanagement.enums.AuthCode;
import com.lodgitechpro.hotelmanagement.enums.ErrorCode;
import com.lodgitechpro.hotelmanagement.exception.ApplicationException;
import com.lodgitechpro.hotelmanagement.exception.AuthenticationException;
import com.lodgitechpro.hotelmanagement.repository.EmployeeRepository;
import com.lodgitechpro.hotelmanagement.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final EmployeeRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        repository.findByEmail(request.getEmail())
                .ifPresent(employee -> {
                    throw new ApplicationException(ErrorCode.USER_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
                });

        var employee = Employee.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var savedUser = repository.save(employee);
        var jwtToken = jwtService.generateToken(employee);
        var refreshToken = jwtService.generateRefreshToken(employee);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticate(request.getEmail(), request.getPassword());
        var employee = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(employee);
        var refreshToken = jwtService.generateRefreshToken(employee);
        revokeAllUserTokens(employee);
        saveUserToken(employee, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .authStatus(AuthCode.AUTH_SUCCESS.getMessage())
                .build();
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException(AuthCode.ACCOUNT_DISABLED);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException(AuthCode.INVALID_CREDENTIALS);
        }
    }

    private void saveUserToken(Employee employee, String jwtToken) {
        var token = Token.builder()
                .employee(employee)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Employee employee) {
        var validUserTokens = tokenRepository.findAllValidTokenByEmployee(employee.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var employee = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, employee)) {
                var accessToken = jwtService.generateToken(employee);
                revokeAllUserTokens(employee);
                saveUserToken(employee, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
