package com.haroun.ssrs.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "charts")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Chart implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "charts_sequence";

    @Id
    private long chartId;
    private String settings;

    @PersistenceConstructor
    public Chart(long chartId, String settings) {
        this.chartId = chartId;
        this.settings = settings;
    }
}
