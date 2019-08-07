package io.cronica.api.pdfgenerator.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
public class FileUtils {

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
            return image;
        }
        catch (WriterException ex) {
            log.error("[UTILITY] unable to generate QR code", ex);
            throw new RuntimeException(ex);
        }
    }
}
