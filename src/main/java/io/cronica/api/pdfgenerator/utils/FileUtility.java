package io.cronica.api.pdfgenerator.utils;

import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Set;
import java.util.stream.Collectors;

import static io.cronica.api.pdfgenerator.utils.Constants.*;

/**
 * Class with few utility methods for working with files.
 *
 * @author Dmytro Kohut
 *
 * @version 1.0
 */
@Slf4j
public class FileUtility {

    /**
     * Check whether file, sent in request, is met conditions for ZIP archive
     * which could be processed in API.
     *
     * @param buffer
     *          - file which need to check
     *
     * @throws InvalidRequestException - if file is not ZIP archive or it is empty
     */
    public static void validateZipArchive(final DataBuffer buffer) {
        if (buffer.readableByteCount() <= 0) {
            log.info("[UTILITY] sent file is empty");
            throw new InvalidRequestException("File is empty");
        }
        validateZIPMagicNumbers(buffer);
    }

    private static void validateZIPMagicNumbers(final DataBuffer dataBuffer) {
        try {
            final InputStream inputStream = dataBuffer.asInputStream();

            final String magicNumbers = readMagicNumbers(inputStream, 8);

            if (!magicNumbers.equalsIgnoreCase(ZIP_ARCHIVE_MAGIC_NUMBER)) {
                log.info("[UTILITY] invalid file's magic numbers: {}", magicNumbers);
                throw new InvalidRequestException("Sent file is not ZIP archive");
            }

        } catch (IOException ex) {
            log.info("[UTILITY] exception while validating file's magic numbers");
            throw new InvalidRequestException("Sent file is not ZIP archive");
        }
    }

    private static String readMagicNumbers(
            final InputStream inputStream, int characters) throws IOException {

        try {
            if (inputStream.available() < 8) {
                throw new InvalidRequestException("Sent file too small");
            }

            final ReadableByteChannel fileChannel = Channels.newChannel(inputStream);
            final ByteBuffer bb = ByteBuffer.allocate(8);
            bb.order(ByteOrder.BIG_ENDIAN);
            fileChannel.read(bb);
            bb.flip();

            if (characters > 16) {
                characters = 16;
            }

            return Long.toHexString(bb.getLong()).substring(0, characters);

        } catch (StringIndexOutOfBoundsException ex) {
            return "";
        }
    }

    /**
     * Check whether exists template in list of files.
     *
     * @param files
     *          - list of files
     * @return file of template
     *
     * @throws InvalidRequestException - if exists template file does not found or
     * found more than 1 file
     */
    public static File validateTemplateFile(final Set<File> files) {
        final Set<File> fileSet = filterFilesByPrefix(TEMPLATE_FILE_PREFIX, files);

        if (fileSet.size() == 0) {
            log.info("[UTILITY] main template file does not found");
            throw new InvalidRequestException("Template file does not found");
        } else if (fileSet.size() > 1) {
            log.info("[UTILITY] found more than 1 file with 'body' prefix");
            throw new InvalidRequestException("Found more than 1 file with 'body' prefix");
        } else {
            return (File) fileSet.toArray()[0];
        }
    }

    /**
     * Check whether header file in list of files.
     *
     * @param files
     *          - list of files
     * @return header file
     *
     * @throws InvalidRequestException - if found more than 1 header files
     */
    public static File validateHeaderFile(final Set<File> files) {
        final Set<File> fileSet = filterFilesByPrefix(HEADER_FILE_PREFIX, files);

        if (fileSet.size() == 0) {
            return null;
        } else if (fileSet.size() > 1) {
            log.info("[UTILITY] found more than 1 file with 'header' prefix");
            throw new InvalidRequestException("Found more than 1 file with 'header' prefix");
        } else {
            return (File) fileSet.toArray()[0];
        }
    }

    /**
     * Check whether footer file in list of files.
     *
     * @param files
     *          - list of files
     * @return footer file
     *
     * @throws InvalidRequestException - if found more than 1 footer files
     */
    public static File validateFooterFile(final Set<File> files) {
        final Set<File> fileSet = filterFilesByPrefix(FOOTER_FILE_PREFIX, files);

        if (fileSet.size() == 0) {
            return null;
        } else if (fileSet.size() > 1) {
            log.info("[UTILITY] found more than 1 file with 'footer' prefix");
            throw new InvalidRequestException("Found more than 1 file with 'footer' prefix");
        } else {
            return (File) fileSet.toArray()[0];
        }
    }

    private static Set<File> filterFilesByPrefix(final String prefix, final Set<File> files) {
        return files.stream()
                .filter(file -> file.getName().startsWith(prefix))
                .collect(Collectors.toSet());
    }
}
