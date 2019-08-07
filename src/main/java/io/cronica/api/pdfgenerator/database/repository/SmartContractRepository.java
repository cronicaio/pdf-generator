package io.cronica.api.pdfgenerator.database.repository;

import io.cronica.api.pdfgenerator.database.model.SmartContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartContractRepository extends JpaRepository<SmartContract, Long> {

    SmartContract findByContractName(String contractName);
}
