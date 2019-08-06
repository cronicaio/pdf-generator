package io.cronica.api.pdfgenerator.database.repository;

import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentCertificateRepository extends JpaRepository<DocumentCertificate, Long> {
}
