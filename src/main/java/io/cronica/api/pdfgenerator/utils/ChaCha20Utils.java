package io.cronica.api.pdfgenerator.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class ChaCha20Utils {

    private static final String ALGORITHM = "ChaCha20";

    /**
     * Encrypt specified plaintext using 'ChaCha20' algorithm.
     *
     * @param plaintext
     *          - data to encrypt
     * @param key
     *          - secret key for encryption
     * @return ciphertext
     */
    public static byte[] encrypt(final byte[] plaintext, final SecretKey key) {
        if (plaintext == null || plaintext.length == 0) {
            log.debug("[CHACHA20] empty array. Skip encryption.");
            return new byte[]{};
        }

        final byte[] nonceBytes = new byte[12];
        final int counter = 5;
        log.debug("[CHACHA20] start encrypting data");
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            final ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonceBytes, counter);
            final SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);

            final byte[] encryptedData = cipher.doFinal(plaintext);
            log.debug("[CHACHA20] data successfully encrypted");

            return encryptedData;
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            log.error("[CHACHA20] exception while instantiating cipher", ex);
        }
        catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            log.error("[CHACHA20] exception while encrypting plaintext", ex);
        }
        return new byte[]{};
    }

    /**
     * Decrypt specified ciphertext using 'ChaCha20' algorithm.
     *
     * @param ciphertext
     *          - data to decrypt
     * @param key
     *          - secret key for decryption
     * @return plaintext
     */
    public static byte[] decrypt(final byte[] ciphertext, final SecretKey key) {
        if (ciphertext == null || ciphertext.length == 0) {
            log.debug("[CHACHA20] empty array. Skip decryption.");
            return new byte[]{};
        }

        final byte[] nonceBytes = new byte[12];
        final int counter = 5;
        log.debug("[CHACHA20] start decrypting data");
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            final ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonceBytes, counter);
            final SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);

            final byte[] decryptedText = cipher.doFinal(ciphertext);
            log.debug("[CHACHA20] data successfully decrypted");

            return decryptedText;
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            log.error("[CHACHA20] exception while instantiating cipher", ex);
        }
        catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            log.error("[CHACHA20] exception while decrypting ciphertext", ex);
        }
        return new byte[]{};
    }
}
