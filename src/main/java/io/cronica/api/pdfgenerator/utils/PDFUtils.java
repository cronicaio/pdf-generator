package io.cronica.api.pdfgenerator.utils;

import io.cronica.api.pdfgenerator.exception.CronicaRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
public class PDFUtils {

    public static BufferedImage convertPdfToImage(byte[] pdfDocument) {
        try (ByteArrayInputStream pdfStream = new ByteArrayInputStream(pdfDocument)) {
            PDDocument document = PDDocument.load(pdfStream);
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage result = renderer.renderImage(0);
            document.close();
            return result;
        } catch (Exception e) {
            log.error("[PDF-UTILS] failed rendering image from pdf document", e);
            throw new CronicaRuntimeException("Error on rendering image from pdf", e);
        }
    }

    public static ByteArrayOutputStream asPng(BufferedImage image) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", outputStream);
            return outputStream;
        } catch (Exception e) {
            log.error("[PDF-UTILS] failed converting image to png", e);
            throw new CronicaRuntimeException("Error on converting image to png", e);
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
