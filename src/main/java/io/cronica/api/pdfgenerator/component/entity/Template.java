package io.cronica.api.pdfgenerator.component.entity;

import io.cronica.api.pdfgenerator.component.dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Template {

    private Integer templateID;

    private String name;

    private ItemDTO items;
}
