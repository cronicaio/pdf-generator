package io.cronica.api.pdfgenerator.component.redis;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RedisDocument {

    private String type;

    private String documentID;
}
