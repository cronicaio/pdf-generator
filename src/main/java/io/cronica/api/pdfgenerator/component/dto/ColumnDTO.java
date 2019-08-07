package io.cronica.api.pdfgenerator.component.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDTO implements Serializable {

    private String columnName;
}
