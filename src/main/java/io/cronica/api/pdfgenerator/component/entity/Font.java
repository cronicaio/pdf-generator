package io.cronica.api.pdfgenerator.component.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Font {

    private String fontFamily;

    private String base64Content;

    private String fontType;
}
