package com.lodgitechpro.hotelmanagement.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Auditable implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
        @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_seq", allocationSize = 1)
        private int id;

        @Column(name = "FIRST_NAME")
        private String firstName;

        @Column(name = "USERNAME")
        private String username;

        @Column(name = "LAST_NAME")
        private String lastName;

        @Column(name = "email")
        private String email;

        @Column(name = "password")
        private String password;

        @OneToMany(mappedBy = "employee")
        private Set<EmployeeRole> employeeRoles = new HashSet<>();

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
        }

        @Override
        public String getPassword() {
                return this.password;
        }

        @Override
        public String getUsername() {
                return email;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
