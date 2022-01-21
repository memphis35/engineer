package com.example.engineer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "email_verification")
@NoArgsConstructor
@Getter
@Setter
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "uuid", nullable = false, updatable = false)
    private String uuid;

    @Column(name = "expired", nullable = false, insertable = false, updatable = false)
    private LocalDateTime expiredAt;

    public EmailVerification(String email) {
        this.email = email;
        this.uuid = UUID.randomUUID().toString();
    }
}
