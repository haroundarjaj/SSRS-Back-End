package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "formulas")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Formula implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "formulas_sequence";

    @Id
    private long formulaId;
    private String name;
    private String equation;
    private String type;
    private Date creationTime;

    @DBRef
    private AppUser user;
}
