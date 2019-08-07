package io.cronica.api.pdfgenerator.database.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "smart_contracts_data", indexes = {
        @Index(name = "ux_smart_contracts_data_address", columnList = "address")
})
public class SmartContract {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @Column(name = "contract_name", length = 255, nullable = false, unique = true)
    private String contractName;

    public SmartContract(String address, String contractName) {
        this.address = address;
        this.contractName = contractName;
    }
}
