package io.cronica.api.pdfgenerator;

import io.cronica.api.pdfgenerator.utils.ChaCha20Utils;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

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
}
