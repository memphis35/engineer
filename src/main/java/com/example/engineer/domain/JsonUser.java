package com.example.engineer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JsonUser {
    private String email;
    private String firstName;
    private String lastName;
    private String tabId;
    private String position;
    private String department;
}
