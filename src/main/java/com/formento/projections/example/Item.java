package com.formento.projections.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
public class Item {

    @Id
    private final String id;
    private final String description;

}
