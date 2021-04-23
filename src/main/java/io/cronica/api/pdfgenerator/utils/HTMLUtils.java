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
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

import static io.cronica.api.pdfgenerator.utils.Constants.*;

@Slf4j
public class HTMLUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

    public static final Map<String, Supplier<String>> PRE_DEFINED_KEYS;

    static {
        PRE_DEFINED_KEYS = new HashMap<String, Supplier<String>>() {{
            put(Constants.KEY_VERIFICATION_TIMESTAMP, HTMLUtils::get_current_date);
            put(Constants.KEY_VERIFICATION_TIMESTAMP_JO, HTMLUtils::get_current_date_eet);
        }};
    }

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
        for (Entry<String, Supplier<String>> entry : PRE_DEFINED_KEYS.entrySet()) {
            if (fullDocument.contains(entry.getKey())) {
                final String value = entry.getValue().get();
                fullDocument = StringUtils.replace(fullDocument, entry.getKey(), value);
            }
        }
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
                    final Elements filterTables = filterTables(tables);
                    try {
                        final String theadContent = removeThead(htmlDocument, param);
                        final String tfootContent = removeTfoot(htmlDocument, param);
                        final String tbodyContent = removeTbody(htmlDocument, param);

                        if (!StringUtils.isEmpty(theadContent)) {
                            htmlDocument.selectFirst("table#" + param).append("<thead>" + theadContent + "</thead>");
                        }
                        htmlDocument.selectFirst("table#" + param).append(builder.toString());
                        if (!StringUtils.isEmpty(tbodyContent)) {
                            htmlDocument.selectFirst("table#" + param).append("<tbody>" + tbodyContent + "</tbody>");
                        }
                        if (!StringUtils.isEmpty(tfootContent)) {
                            htmlDocument.selectFirst("table#" + param).append("<tfoot>" + tfootContent + "</tfoot>");
                        }
                    }
                    catch (NullPointerException ex) {
                        for (Element table : filterTables) {
                            if (table.hasAttr("data-tablename")
                                && table.attr("data-tablename").equals(param)) {
                                final String theadContent = removeThead(table);
                                final String tfootContent = removeTfoot(table);
                                final String tbodyContent = removeTbody(table);

                                if (!StringUtils.isEmpty(theadContent)) {
                                    table.selectFirst("table").append("<thead>" + theadContent + "</thead>");
                                }
                                table.selectFirst("table").append(builder.toString());
                                if (!StringUtils.isEmpty(tbodyContent)) {
                                    table.selectFirst("table").append("<tbody>" + tbodyContent + "</tbody>");
                                }
                                if (!StringUtils.isEmpty(tfootContent)) {
                                    table.selectFirst("table").append("<tfoot>" + tfootContent + "</tfoot>");
                                }
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

    private static Elements filterTables(final Elements tables) {
        final Elements filteredTables = new Elements();
        for (Element table : tables) {
            if (!table.hasAttr("ignore")) {
                filteredTables.add(table);
            }
        }
        return filteredTables;
    }

    private static String removeThead(final Document htmlDocument, final String param) {
        return removeThead(htmlDocument.selectFirst("table#" + param));
    }

    private static String removeThead(final Element table) {
        final Element element = table.selectFirst("thead");
        if (element != null) {
            final String theadContent = element.html();
            table.selectFirst("thead").remove();
            return theadContent;
        }
        return StringUtils.EMPTY;
    }

    private static String removeTfoot(final Document htmlDocument, final String param) {
        return removeTfoot(htmlDocument.selectFirst("table#" + param));
    }

    private static String removeTfoot(final Element table) {
        final Element element = table.selectFirst("tfoot");
        if (element != null) {
            final String tfootContent = element.html();
            table.selectFirst("tfoot").remove();
            return tfootContent;
        }
        return StringUtils.EMPTY;
    }

    private static String removeTbody(final Document htmlDocument, final String param) {
        return removeTbody(htmlDocument.selectFirst("table#" + param));
    }

    private static String removeTbody(final Element table) {
        final Element element = table.selectFirst("tbody");
        if (element != null) {
            final String tbodyContent = element.html();
            table.selectFirst("tbody").remove();
            return tbodyContent;
        }
        return StringUtils.EMPTY;
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

    @Deprecated
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

    public static void insertBase64ImageIntoImg(
            String selector, final String base64Image, final String altText, final String mimeType, final File template
    ) throws IOException {
        final Document document = readDocument(template);
        final Elements imgs = document.select(selector);
        for (Element img : imgs) {
            if ( hasValidQRCodeAttr(img) ) {
                img.attr("src", "data:" + mimeType + BASE64_CONTENT_OPEN + base64Image);
                img.attr("alt", altText);
            }
        }
        FileUtils.writeStringToFile(template, document.html(), StandardCharsets.UTF_8);
    }

    public static void insertQrCodeImageBase64(
            final String base64Image, final String altText, final String mimeType, final File template
    ) throws IOException {
        insertBase64ImageIntoImg("img", base64Image, altText, mimeType, template);
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

    private static String get_current_date() {
        Instant now = Instant.now();
        ZonedDateTime current = now.atZone(ZoneId.systemDefault());
        return current.format(formatter);
    }

    private static String get_current_date_eet() {
        Instant now = Instant.now();
        ZonedDateTime current = now.atZone(ZoneId.of("EET"));
        return current.format(formatter);
    }
}
