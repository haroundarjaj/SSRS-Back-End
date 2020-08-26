package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(collection = "dashboard")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dashboard implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "dashboard_sequence";

    @Id
    private long dashboardId;
    private Date lastEditTime;

    @DBRef
    private AppUser user;

    @DBRef
    private List<Chart> charts;
}
