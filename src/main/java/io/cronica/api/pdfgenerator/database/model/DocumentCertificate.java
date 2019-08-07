package io.cronica.api.pdfgenerator.database.model;

import io.cronica.api.pdfgenerator.component.dto.DataJsonDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@Entity
@Table(name = "document_certificates", indexes = {
        @Index(columnList = "id", name = "ux_document_certificates_id"),
        @Index(columnList = "document_id", name = "ux_document_certificates_document_id"),
        @Index(columnList = "document_name", name = "ix_document_certificates_document_name"),
        @Index(columnList = "recipient_name", name = "ix_document_certificates_recipient_name"),
        @Index(columnList = "issue_timestamp", name = "ix_document_certificates_issue_timestamp")
})
@TypeDefs( {
        @TypeDef(name = "DataJsonDtoType", typeClass = DataJsonDTO.class)
})
public class DocumentCertificate {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_id", unique = true, nullable = false)
    @Size(min = 0, max = 85)
    private String documentID;

    @Column(name = "document_name")
    @Size(min = 0, max = 255)
    private String documentName;

    @Column(name = "recipient_name", nullable = false)
    @Size(min = 0, max = 150)
    private String recipientName;

    @Column(name = "issue_timestamp", nullable = false)
    private Long issueTimestamp;

    @Column(name = "is_structured", nullable = false)
    private Boolean isStructured;
}
