package com.formento.projections.house;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@ToString
public class House {

    @Id
    private final String id;

    @Setter
    @JsonProperty(access = READ_ONLY)
    private String user;
    private final String address;

}
