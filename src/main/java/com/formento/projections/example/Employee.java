package com.formento.projections.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
public class Employee {

    @Id
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String title;

}
