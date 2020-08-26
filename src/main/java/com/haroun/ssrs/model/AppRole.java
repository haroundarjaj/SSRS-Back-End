package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(value = "roles")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppRole implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "roles_sequence";

    @Id
    private long roleId;
    private String role;
}
