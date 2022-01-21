package com.example.engineer.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Entity
@Table(schema = "public", name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Department.class)
    private Department department;

    @Column(name = "created", updatable = false, insertable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @Column(name = "role", nullable = false, insertable = false, columnDefinition = "varchar(256) default 'ROLE_READ_ONLY'")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

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

    @OneToMany
    @JoinColumn(name = "customer_id")
    private Set<Task> tasks;

    @Override
    public String toString() {
        return String.format("id: %s, email: %s, name: %s, auths: %s", id, email, name, role);
    }
}
