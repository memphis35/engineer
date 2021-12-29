package com.example.engineer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    public Authority(@NotBlank String authority) {
        this.authority = authority;
    }
}
