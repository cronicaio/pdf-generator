package io.cronica.api.pdfgenerator.component.kafka.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratePdfRequest {

    public enum StorageType { NOT_SET, CACHE, BLOCKCHAIN, S3 }

    private UUID resultId;
    private Content document;
    private Content template;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private String id;
        private StorageType storageType;
    }

}
