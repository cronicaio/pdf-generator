package io.cronica.api.pdfgenerator;

import io.cronica.api.pdfgenerator.component.wrapper.TemplateContract;
import io.cronica.api.pdfgenerator.service.TemplateTransactionService;
import io.cronica.api.pdfgenerator.service.TemplateTransactionServiceImpl;
import io.cronica.api.pdfgenerator.utils.ChaCha20Utils;

import org.junit.Test;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.StaticGasProvider;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

public class ChaCha20Tests {

    private static final String ALGORITHM = "ChaCha20";

    private static final String PLAINTEXT =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";

    @Test
    public void encryptAndDecryptText() throws NoSuchAlgorithmException {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        final SecretKey secretKey = keyGenerator.generateKey();

        final byte[] plaintextBytes = PLAINTEXT.getBytes(StandardCharsets.UTF_8);
        final byte[] ciphertextBytes = ChaCha20Utils.encrypt(plaintextBytes, secretKey);

        assertThat(plaintextBytes).isNotEqualTo(ciphertextBytes);

        final byte[] decryptedData = ChaCha20Utils.decrypt(ciphertextBytes, secretKey);
        final String decryptedString = new String(decryptedData, StandardCharsets.UTF_8);

        assertThat(decryptedString).isEqualTo(PLAINTEXT);
    }

    @Test
    public void encryptEmptyArray() throws NoSuchAlgorithmException {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        final SecretKey secretKey = keyGenerator.generateKey();

        final byte[] ciphertext1 = ChaCha20Utils.encrypt(null, secretKey);
        final byte[] ciphertext2 = ChaCha20Utils.encrypt(new byte[]{}, secretKey);

        assertThat(ciphertext1).isEmpty();
        assertThat(ciphertext2).isEmpty();
    }

    @Test
    public void decryptEmptyArray() throws NoSuchAlgorithmException {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        final SecretKey secretKey = keyGenerator.generateKey();

        final byte[] plaintext1 = ChaCha20Utils.decrypt(null, secretKey);
        final byte[] plaintext2 = ChaCha20Utils.decrypt(new byte[]{}, secretKey);

        assertThat(plaintext1).isEmpty();
        assertThat(plaintext2).isEmpty();
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    @Test
    public void unpackTemplateBody() throws Exception {
        final String templatesResponseAddress = "0xf63daaf91d699a92842036b73a11b974969ba366";
        final HttpService httpService = new HttpService("http://localhost:22000");
        Quorum quorum = Quorum.build(httpService);
        Credentials credentials = Credentials.create(ECKeyPair.create(BigInteger.ZERO));
        TemplateTransactionService service = new TemplateTransactionServiceImpl(quorum, credentials);
        TemplatesRegistry templatesRegistry = new TemplatesRegistry(templatesResponseAddress, quorum, credentials);
        long templatesCount = templatesRegistry.getCountOfTemplates().send().getValue().longValue();
        for (long i = 0; i < templatesCount; i++) {
            Tuple2<Utf8String, Utf8String> templateInfo = templatesRegistry.getTemplate(new Uint256(i)).send();
            final String templateAddress = templateInfo.getValue1().getValue();
            final String templateName = templateInfo.getValue2().getValue();
            System.out.printf("Loading template %s at address %s\n", templateName, templateAddress);
            TemplateContract contract = service.loadTemplate(templateAddress);

            System.out.println("Loading body");
            int errors = 0;
            try {
                String body = service.getMainContentOfTemplate(contract);
                writeInto(body, "./templates/" + templateAddress + "_" + templateName.replaceAll("\\W+", ""), "body.html");
            } catch (Exception e) {
                System.out.printf("Failed loading template's body of %s\n", templateAddress);
                System.err.println(e.getMessage());
                errors++;
            }

            System.out.println("Loading header");
            try {
                String header = service.getHeaderContentOfTemplate(contract);
                writeInto(header, "./templates/" + templateAddress + "_" + templateName.replaceAll("\\W+", ""), "header.html");
            } catch (Exception e) {
                System.out.printf("Failed loading template's header of %s\n", templateAddress);
                System.err.println(e.getMessage());
                errors++;
            }

            System.out.println("Loading footer");
            try {
                String footer = service.getFooterContentOfTemplate(contract);
                writeInto(footer, "./templates/" + templateAddress + "_" + templateName.replaceAll("\\W+", ""), "footer.html");
            } catch (Exception e) {
                System.out.printf("Failed loading template's footer of %s\n", templateAddress);
                System.err.println(e.getMessage());
                errors++;
            }

            final String color;
            if (errors == 1 || errors == 2) {
                color = ANSI_YELLOW;
            } else if (errors == 3) {
                color = ANSI_RED;
            } else {
                color = ANSI_GREEN;
            }
            System.out.printf("%sLoading template %s finished%s\n", color, templateAddress, ANSI_RESET);
        }
    }

    static void writeInto(String content, String path, String filename) throws IOException {
        if (content == null || content.length() == 0) {
            return;
        }
        File directory = new File(path);
        if (!directory.exists()){
            directory.mkdirs();
        }
        File file = new File(path + "/" + filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    class TemplatesRegistry extends Contract {

        public static final String FUNC_GETTEMPLATE = "getTemplate";

        public static final String FUNC_GETCOUNTOFTEMPLATES = "getCountOfTemplates";

        public TemplatesRegistry(String contractAddress, Web3j web3j, Credentials credentials) {
            super("", contractAddress, web3j, credentials, new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO));
        }

        public RemoteCall<Uint256> getCountOfTemplates() {
            final Function function = new Function(FUNC_GETCOUNTOFTEMPLATES, 
                    Arrays.<Type>asList(), 
                    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
            return executeRemoteCallSingleValueReturn(function);
        }

        public RemoteCall<Tuple2<Utf8String, Utf8String>> getTemplate(Uint256 _index) {
            final Function function = new Function(FUNC_GETTEMPLATE, 
                    Arrays.<Type>asList(_index), 
                    Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
            return new RemoteCall<Tuple2<Utf8String, Utf8String>>(
                    new Callable<Tuple2<Utf8String, Utf8String>>() {
                        @Override
                        public Tuple2<Utf8String, Utf8String> call() throws Exception {
                            List<Type> results = executeCallMultipleValueReturn(function);
                            return new Tuple2<Utf8String, Utf8String>(
                                    (Utf8String) results.get(0), 
                                    (Utf8String) results.get(1));
                        }
                    });
        }

    }
}
