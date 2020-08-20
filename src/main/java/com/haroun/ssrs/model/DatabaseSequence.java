package com.haroun.ssrs.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "database_sequences")
@Getter
@Setter
public class DatabaseSequence implements Serializable {

    @Id
    private String id;

    private long seq;

}
