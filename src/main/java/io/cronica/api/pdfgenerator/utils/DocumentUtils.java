package io.cronica.api.pdfgenerator.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.cronica.api.pdfgenerator.component.dto.DataJsonDTO;
import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DocumentUtils {

    private static final int DOCUMENT_ID_STRING_LENGTH = 82;
    private static final Pattern HEXADECIMAL_PATTERN = Pattern.compile("0x\\p{XDigit}+");

    public static String getSha256(final byte[] bytes) {
        return Numeric.toHexString(Hash.sha256(bytes));
    }

    public static String readDocumentAddress(final String documentID) {
        if (!isValidDocumentID(documentID)) {
            log.info("[UTILITY] sent string is not ID of document");
            throw new InvalidRequestException("Sent string is not ID of document");
        }
        return StringUtils.substring(documentID, 0, Address.LENGTH_IN_HEX + 2);
    }

    public static boolean isValidDocumentID(final String documentID) {
        return StringUtils.isNotEmpty(documentID) && isHexadecimal(documentID) &&
                documentID.length() == DOCUMENT_ID_STRING_LENGTH;
    }

    public static boolean isHexadecimal(String input) {
        final Matcher matcher = HEXADECIMAL_PATTERN.matcher(input);
        return matcher.matches();
    }

    public static Map<String, Object> modifyParameters(final Map<String, Object> initialMap) {
        final Map<String, Object> modifiedMap = new HashMap<>();
        for (String key : initialMap.keySet()) {
            // if parameter in JSON is mapped to collection - skip it
            if (initialMap.get(key) instanceof Collection<?>) {
                modifiedMap.put(key, initialMap.get(key));
                continue;
            }
            modifiedMap.put("@@@" + key + "@@@", initialMap.get(key));
        }

        return modifiedMap;
    }

    public static void generateQRCodeImage(
            final String text, final int width, final int height, final String filePath) {
        try {
            BufferedImage image = generateQRCodeImage(text, width, height);
            ImageIO.write(image, "PNG", new File(filePath));
            log.info("[UTILITY] QR-code image has been generated");

        } catch (IOException ex) {
            log.error("[UTILITY] unable to generate QR code", ex);
            throw new RuntimeException(ex);
        }
    }

    public static BufferedImage generateQRCodeImage(final String text, final int width, final int height) {
        try {
            log.info("[UTILITY] generating new QR-code image");
            final Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hintMap.put(EncodeHintType.MARGIN, 1);      /* default = 4 */

            final QRCodeWriter qrCodeWriter = new QRCodeWriter();
            final BitMatrix byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hintMap);
            final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            final Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setBackground(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            log.info("[UTILITY] QR-code image has been generated");
//            return addOverlayLogo(image); // Temporary disabled overlay with Cronica logo
            return image;
        }
        catch (WriterException ex) {
            log.error("[UTILITY] unable to generate QR code", ex);
            throw new RuntimeException(ex);
        }
    }

    public static Map<String, Object> convertJsonStringToMap(final String jsonString) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
    }

    public static String convertMapToJsonString(final Map<String, Object> map) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    /**
     * Copying data from DataJsonDto object to map with structure which understands
     * Jaspersoft library used by this API.
     *
     * @param dataJson
     *          - object that contains data that need to restructure
     * @return mapping with a structure that is understandable for the Jaspersoft library
     */
    public static String convertDataJsonToString(DataJsonDTO dataJson) {
        final Map<String, Object> map = new HashMap<>();
        if (dataJson.getFields() != null) {
            map.putAll(dataJson.getFields());
        }

        if (dataJson.getTables() != null) {
            for (String key : dataJson.getTables().keySet()) {
                map.put(key, dataJson.getTables().get(key));
            }
        }

        try {
            return convertMapToJsonString(map);
        } catch (IOException e) {
            log.error("[UTILITY] unable to convert json object to string", e);
            return "{}";
        }
    }

    private static BufferedImage addOverlayLogo(final BufferedImage qrImage) {
        try {
            Resource resource = new ClassPathResource("static/logo.png");
            final BufferedImage logoImage = ImageIO.read(resource.getInputStream());
            final Image logoImageScaledInstance = logoImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            final BufferedImage scaledLogo = toBufferedImage(logoImageScaledInstance);

            float deltaHeight = (float) (qrImage.getHeight() - scaledLogo.getHeight());
            float deltaWidth = (float) (qrImage.getWidth() - scaledLogo.getWidth());

            BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) combined.getGraphics();

            g.drawImage(qrImage, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            int scaledDeltaWidth = Math.round(deltaWidth / 2);
            int scaledDeltaHeight = Math.round(deltaHeight / 2);
            g.drawImage(scaledLogo, scaledDeltaWidth, scaledDeltaHeight, null);

            return combined;
        } catch (Exception e) {
            log.error("[UTILITY] error during generation logo overlay", e);
            return qrImage;
        }
    }

    private static BufferedImage toBufferedImage(final Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();

        return bufferedImage;
    }

}
