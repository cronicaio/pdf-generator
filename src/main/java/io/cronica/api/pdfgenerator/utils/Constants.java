package io.cronica.api.pdfgenerator.utils;

import java.math.BigInteger;

public interface Constants {

    BigInteger GAS_PRICE = BigInteger.ZERO;
    BigInteger GAS_LIMIT = BigInteger.valueOf(2_000_000L);

    String FONT_FACE_OPEN = "@font-face {";
    String FONT_FACE_CLOSE = "}";
    String FONT_FAMILY_OPEN = "font-family:";
    String FONT_FAMILY_CLOSE = ";";
    String BASE64_CONTENT_OPEN = ";base64,";
    String BASE64_CONTENT_CLOSE = ");";
    String FONT_TYPE_OPEN = "src: url(data:";
    String FONT_TYPE_SEPARATOR = "/";
    String FILE_SEPARATOR = "--";
    String DEFAULT_FONT_TYPE = "font/truetype";

    String PNG_FILE_EXTENSION = ".png";
    String PATH_TO_FOLDER_WITH_QR_CODE = "./temp/qr/";
}
