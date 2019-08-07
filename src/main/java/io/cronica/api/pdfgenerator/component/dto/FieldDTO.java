package io.cronica.api.pdfgenerator.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class FieldDTO implements Serializable {

    private String name;

    private String systemName;
}
