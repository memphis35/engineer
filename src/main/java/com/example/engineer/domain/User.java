package com.example.engineer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(schema = "public", name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @NotBlank(message = "Email can't be null or empty")
    @Pattern(regexp = "^[0-9A-Za-z_.-]{3,}@[0-9A-Za-z]+\\.[a-z]{2,}", message = "Please, make sure your email is correct")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password can't be null")
    private String password;

    @Column(name = "name")
    @NotBlank(message = "Name can't be null or blank")
    private String name;

    @ManyToOne(targetEntity = Department.class)
    private Department department;

    @Column(name = "created")
    private LocalDateTime createdAt;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "password_changed")
    private LocalDateTime passwordChanged;

    @Column(name = "is_enabled")
    private Boolean enabled = true;

    @Column(name = "is_non_locked")
    private Boolean nonLocked = true;

    @Column(name = "is_account_non_expired")
    private Boolean accountNonExpired = true;

    @Column(name = "is_credentials_non_expired")
    private Boolean credentialNonExpired = true;

    @Override
    public String toString() {
        return String.format("id: %s, email: %s, name: %s, auths: %s", id, email, name, role);
    }
}
