package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "formulas")
@Data
@NoArgsConstructor
@Getter
@Setter
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

    @PersistenceConstructor
    public Formula(long formulaId, String name, String equation, String type, Date creationTime, AppUser user) {
        this.formulaId = formulaId;
        this.name = name;
        this.equation = equation;
        this.type = type;
        this.creationTime = creationTime;
        this.user = user;
    }
}
