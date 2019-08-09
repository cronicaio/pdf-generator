package io.cronica.api.pdfgenerator.utils;

import io.cronica.api.pdfgenerator.component.entity.Font;
import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static io.cronica.api.pdfgenerator.utils.Constants.*;

@Slf4j
public class HTMLUtils {

    public static List<Font> readFonts(final String pathToFolder) throws IOException {
        final List<Font> fontList = new ArrayList<>();
        final File folder = new File(pathToFolder);
        if ( !folder.exists() ) {
            return fontList;
        }

        for (File file : folder.listFiles()) {
            final byte[] fileAsByteArray = FileUtils.readFileToByteArray(file);
            final String encodedContent = Base64.getEncoder().encodeToString(fileAsByteArray);
            final String fontType = getFontType(file.getName());
            final String fontFamily = getFontFamily(file.getName(), fontType);
            fontList.add( new Font(fontFamily, encodedContent, fontType) );
        }

        return fontList;
    }

    private static String getFontType(final String name) {
        String fontType = StringUtils.substringBetween(name, "_", ".");
        if (StringUtils.isEmpty(fontType)) {
            fontType = DEFAULT_FONT_TYPE;
        }
        else {
            fontType = fontType.replace(FILE_SEPARATOR, FONT_TYPE_SEPARATOR);
        }
        return fontType;
    }

    private static String getFontFamily(
            final String fileName, String fontType) {
        fontType = fontType.replace(FONT_TYPE_SEPARATOR, FILE_SEPARATOR);
        return FilenameUtils
                .getBaseName(fileName)
                .replace("_" + fontType, "");
    }

    public static void importFontsIntoHtml(
            final File htmlFile, final List<Font> fontList)
            throws IOException {
        final Document document = readDocument(htmlFile);
        final Element head = document.head();

        final String styleContent = removeStyleContentIfFound(document);
        head.append("<style></style>");

        final Element style = document.selectFirst("style");
        for (Font font : fontList) {
            style.append( generateFontFaceBlock(font) );
        }
        style.append(styleContent);

        FileUtils.writeStringToFile(htmlFile, document.html(), StandardCharsets.UTF_8);
    }

    private static String removeStyleContentIfFound(final Document document) {
        final Element style = document.selectFirst("style");
        if (style != null) {
            style.remove();
            return style.html();
        }
        return "";
    }

    private static String generateFontFaceBlock(final Font font) {
        return "\n" + FONT_FACE_OPEN + "\n"
                + FONT_FAMILY_OPEN
                + " '" + font.getFontFamily() + "'"
                + FONT_FAMILY_CLOSE + "\n"
                + FONT_TYPE_OPEN + font.getFontType()
                + BASE64_CONTENT_OPEN
                + font.getBase64Content()
                + BASE64_CONTENT_CLOSE
                + "\n" + FONT_FACE_CLOSE + "\n";
    }

    public static String modifyTemplate(
            Map<String, Object> parameters, final File template) throws IOException {

        parameters = DocumentUtils.modifyParameters(parameters);
        final Document htmlDocument = readDocument(template);

        try {
            log.info("[HTML] inserting rows into tables of HTML document");
            addRowsToTables(parameters, htmlDocument);
            log.info("[HTML] rows have been inserted into tables of HTML document");
        }
        catch (NullPointerException ex) {
            log.info("[HTML] one or few given parameters is not matching", ex);
            throw new InvalidRequestException("One or few given parameters is not matching", ex);
        }

        log.info("[HTML] replacing variables in HTML document with values");
        String fullDocument = htmlDocument.html();
        for (String key : parameters.keySet()) {
            if ( !(parameters.get(key) instanceof Collection<?>) && fullDocument.contains(key)) {
                final String parameter = parameters.get(key).toString();
                fullDocument = StringUtils.replace(fullDocument, key, parameter);
            }
        }
        log.info("[HTML] variables in HTML document have been replaced with values");

        return fullDocument;
    }

    private static void addRowsToTables(final Map<String, Object> parameters, final Document htmlDocument) {
        for (String param : parameters.keySet()) {
            if (parameters.get(param) instanceof Collection<?>) {
                if (parameters.get(param) == null) {
                    continue;
                }
                if (parameters.get(param) instanceof List<?>) {
                    final List<List<Object>> rows = (List<List<Object>>) parameters.get(param);
                    final StringBuilder builder = new StringBuilder();
                    for (List<Object> columns : rows) {
                        addRow(builder, columns);
                    }
                    final Elements tables = getTablesFrom(htmlDocument);
                    try {
                        htmlDocument.selectFirst("table#" + param).append(builder.toString());
                    }
                    catch (NullPointerException ex) {
                        for (Element table : tables) {
                            if (table.hasAttr("data-tablename")
                                    && table.attr("data-tablename").equals(param)) {
                                table.selectFirst("table").append(builder.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    private static void addRow(
            final StringBuilder stringBuilder, final List<Object> columns) {
        stringBuilder.append("<tr>");
        for (Object column : columns) {
            addColumn(stringBuilder, column);
        }
        stringBuilder.append("</tr>");
    }

    private static void addColumn(
            final StringBuilder stringBuilder, final Object column) {
        stringBuilder.append("<td>");
        stringBuilder.append(column.toString());
        stringBuilder.append("</td>");
    }

    private static Elements getTablesFrom(final Document htmlDocument) {
        Elements tableElements = htmlDocument.select("figure.table");
        if (tableElements == null || tableElements.size() == 0) {
            tableElements = htmlDocument.select("table");
        }
        return tableElements;
    }

    public static boolean findQRCodeImageTags(final File template) throws IOException {
        final Document document = readDocument(template);
        final Elements imgs = document.select("img");
        for (Element img : imgs) {
            if ( hasValidQRCodeAttr(img) ) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasValidQRCodeAttr(final Element img) {
        return img.hasAttr("qrcode")
               && img.attr("qrcode").equals("true");
    }

    public static void insertQrCodeImagePathAndAltText(
            final String qrCodeImagePath, final String altText, final File template
    ) throws IOException {
        final Document document = readDocument(template);
        final Elements imgs = document.select("img");
        for (Element img : imgs) {
            if ( hasValidQRCodeAttr(img) ) {
                img.attr("src", qrCodeImagePath);
                img.attr("alt", altText);
            }
        }
        FileUtils.writeStringToFile(template, document.html(), StandardCharsets.UTF_8);
    }

    private static Document readDocument(final File template) throws IOException {
        final String htmlFileContent = FileUtils.readFileToString(template, StandardCharsets.UTF_8);
        return Jsoup.parse(htmlFileContent);
    }

    public static void insertDocumentLinkIfFound(
            final String documentLink, final File template) throws IOException {
        final Document document = readDocument(template);
        final Elements linkTags = document.select("a");
        for (Element linkTag : linkTags) {
            if ( hasValidLinkAttr(linkTag) ) {
                linkTag.attr("href", documentLink);
                linkTag.append(documentLink);
            }
        }
        FileUtils.writeStringToFile(template, document.html(), StandardCharsets.UTF_8);
    }

    private static boolean hasValidLinkAttr(final Element linkTag) {
        return linkTag.hasAttr("document-link")
               && linkTag.attr("document-link").equals("true");
    }
}
