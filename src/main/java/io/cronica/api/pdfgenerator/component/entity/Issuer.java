package io.cronica.api.pdfgenerator.component.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.tuples.generated.Tuple5;

@Setter
@Getter
@ToString
public class Issuer {

    private String bankCode;

    private String bankName;

    private String publicKey;

    private String verifierApiLink;

    private String issuerApiLink;

    private String frontEndLink;

    public static Issuer newInstance(
            final String bankCode,
            final Tuple5<Utf8String, Utf8String, Utf8String, Utf8String, Utf8String> issuerTuple
    ) {
        final Issuer issuer = new Issuer();
        issuer.bankCode = bankCode;
        issuer.bankName = issuerTuple.getValue1().getValue();
        issuer.publicKey = issuerTuple.getValue2().getValue();
        issuer.verifierApiLink = issuerTuple.getValue3().getValue();
        issuer.issuerApiLink = issuerTuple.getValue4().getValue();
        issuer.frontEndLink = issuerTuple.getValue5().getValue();

        return issuer;
    }
}
