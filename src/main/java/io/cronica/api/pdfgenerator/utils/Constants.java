package io.cronica.api.pdfgenerator.utils;

import java.math.BigInteger;

public interface Constants {

    BigInteger GAS_PRICE = BigInteger.ZERO;
    BigInteger GAS_LIMIT = BigInteger.valueOf(2_000_000L);

    String HEADER_FILE_PREFIX = "header";
    String FOOTER_FILE_PREFIX = "footer";

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
    String PATH_TO_PDF_DOCUMENTS = "temp/pdf/";
    String PATH_TO_FOLDER_WITH_QR_CODE = "temp/qr/";
    String DEFAULT_FOOTER_CONTENT_FILE_PATH = "template/footer/DefaultFooterTemplate.html";

    String SEARCH_BY_ID_STRUCTURED_FRONTEND_BASIC_URL = "/#/searchByIdStructured/";

}
