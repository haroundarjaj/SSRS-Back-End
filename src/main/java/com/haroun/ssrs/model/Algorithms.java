package com.haroun.ssrs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "algorithms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Algorithms implements Serializable {
    @Id
    private String algoId;
    private String algoType;
    private String algoDescription;
    private String algoName;
    private String algoFormula;
    private Date created_at;

    @DBRef
    private AppUser user;

}
