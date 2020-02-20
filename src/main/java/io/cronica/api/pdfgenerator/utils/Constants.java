package io.cronica.api.pdfgenerator.utils;

import java.math.BigInteger;

public interface Constants {

    /* HEADERS */
    String REQUEST_ID_HEADER = "X-Request-ID";

    /* LOG4J2 */
    String REQUEST_ID_PARAM = "request.id";

    BigInteger GAS_PRICE = BigInteger.ZERO;
    BigInteger GAS_LIMIT = BigInteger.valueOf(2_000_000L);

    String DOCUMENTS_ROOT = "/documents";
    String SEPARATOR = "/";

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

    String BASE64IMAGE = "base64image";

    /* ARCHIVE */
    String PATH_TO_FOLDER_WITH_ZIP_ARCHIVE_CONTENT = "temp/zip/";
    String ZIP_ARCHIVE_MAGIC_NUMBER = "504B0304";
    String JPEG_IMAGE_MAGIC_NUMBER = "FFD8";
    String PNG_IMAGE_MAGIC_NUMBER = "89504E47";
    String OTF_FONT_MAGIC_NUMBER = "4F54544F00";
    String TTF_FONT_MAGIC_NUMBER_1 = "7472756500";
    String TTF_FONT_MAGIC_NUMBER_2 = "1000000";

    String TEMPLATE_FILE_PREFIX = "body";

    String DEFAULT_FONT_EXTENSION = ".ttf";
    String JASPERSOFT_FILE_EXTENSION = "jrxml";
    String HTML_FILE_EXTENSION = "html";
    String JPEG_FILE_EXTENSION = "jpg";
    String FONT_FILE_EXTENSION = "ttf";

    /* TEMPLATE */
    String BASE64_CONTENT_CLOSE_2 = "');";
    String FONT_TYPE_OPEN_2 = "src: url('data:";
    String SRC_BEGIN = "data:";
    String JPEG_FILE_TYPE = "image/jpeg";
    String PNG_FILE_TYPE = "image/png";
    String JPEG_FILE_EXTENSION_WITH_DOT = ".jpg";
    String PNG_FILE_EXTENSION_WITH_DOT = ".png";

}
