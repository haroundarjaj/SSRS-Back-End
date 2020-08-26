package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "charts")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chart implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "charts_sequence";

    @Id
    private long chartId;
    private String settings;
}
