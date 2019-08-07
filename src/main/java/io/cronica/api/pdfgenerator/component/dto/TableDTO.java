package io.cronica.api.pdfgenerator.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class TableDTO implements Serializable {

    private String tableName;

    private String systemTableName;

    private List<ColumnDTO> columns;
}
