package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "algorithms")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Algorithms implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "algorithms_sequence";

    @Id
    private long algoId;
    private String algoType;
    private String algoDescription;
    private String algoName;
    private String algoFormula;
    private Date created_at;

    @DBRef
    private AppUser user;

}
