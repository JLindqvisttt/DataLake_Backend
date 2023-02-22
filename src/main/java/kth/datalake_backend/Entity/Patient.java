package kth.datalake_backend.Entity;

import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
public class Patient {

    @NotBlank
    private int age;

    @NotBlank
    private Gender gender;

    @NotBlank
    private String ethnicity;
}