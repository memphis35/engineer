package com.example.engineer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "public", name = "task")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    private Integer registrationNumber;

    @Column(name = "registration_date", updatable = false)
    private LocalDate registrationDate;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private User customer;
}
