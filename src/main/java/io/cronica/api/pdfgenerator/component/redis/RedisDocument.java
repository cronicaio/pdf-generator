package io.cronica.api.pdfgenerator.component.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedisDocument {

    private String type;

    private String documentID;
}
