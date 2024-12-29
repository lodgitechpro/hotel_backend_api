package com.lodgitechpro.hotelmanagement.auth.service;

import com.lodgitechpro.hotelmanagement.auth.dtos.ChangePasswordRequest;
import com.lodgitechpro.hotelmanagement.auth.entity.Employee;
import com.lodgitechpro.hotelmanagement.auth.entity.EmployeeRole;
import com.lodgitechpro.hotelmanagement.auth.entity.Role;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.auth.repository.EmployeeRepository;
import com.lodgitechpro.hotelmanagement.auth.repository.EmployeeRoleRepository;
import com.lodgitechpro.hotelmanagement.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository repository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final RoleRepository roleRepository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var employee = (Employee) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), employee.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        employee.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(employee);
    }

    public void saveEmployeeRole(int employeeId, int roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: "+ roleId));

        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: "+ employeeId));

        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setEmployee(employee);
        employeeRole.setRole(role);
        employeeRoleRepository.save(employeeRole);
    }

    public void saveEmployeeRoles(int employeeId, List<Integer> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        if(roles.isEmpty()) {
            log.error("Roles not found with given roleIds. No employee roles will be saved! " + roleIds);
            return;
        }

        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: "+ employeeId));
        List<EmployeeRole> employeeRoles = new ArrayList<>();
        for(Role role : roles) {
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployee(employee);
            employeeRole.setRole(role);
            employeeRoles.add(employeeRole);
        }
        employeeRoleRepository.saveAll(employeeRoles);
        log.info("New {} roles are added with employeeId {}", roles.size(), employee.getId());
    }

    public void copyEmployeeRoles(int fromEmpId, int toEmpId) {

        Employee fromEmployee = repository.findById(fromEmpId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: "+ fromEmpId));

        Employee toEmployee = repository.findById(toEmpId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: "+ toEmpId));


        List<Role> fromRoles = employeeRoleRepository.findByEmployee(fromEmployee).stream()
                .map(EmployeeRole::getRole)
                .toList();

        List<EmployeeRole> employeeRoles = new ArrayList<>();
        for(Role role : fromRoles) {
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployee(toEmployee);
            employeeRole.setRole(role);
            employeeRoles.add(employeeRole);
        }

        employeeRoleRepository.saveAll(employeeRoles);
        log.info("Roles Copy Successful! {} roles are copied fromEmpId {} toEmpId {}", fromRoles.size(), fromEmpId, toEmpId);
    }
}
