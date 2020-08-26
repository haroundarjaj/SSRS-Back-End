package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document (collection = "importedTables")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportedTable implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "importedtables_sequence";

    @Id
    private long tableId;
    private String name;
    private Date creationTime;

    @DBRef
    private AppUser user;
}
