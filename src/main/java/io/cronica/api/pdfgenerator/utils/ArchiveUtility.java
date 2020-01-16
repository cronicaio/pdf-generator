package io.cronica.api.pdfgenerator.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class that contains method for decompressing ZIP archive.
 *
 * @author Dmytro Kohut
 *
 * @version 1.0
 */
@Slf4j
public class ArchiveUtility {

    private static final byte[] BUFFER = new byte[4096];
    private static final String DS_STORE_FILENAME = ".DS_Store";
    private static final String FILE_SEPARATOR = "/";

    /**
     * Decompress files from ZIP archive.
     *
     * @param byteBuffer
     *          - buffer of ZIP archive
     * @param path
     *          - path in filesystem where files will be decompressed
     * @return list of files decompressed from ZIP archive
     */
    public static List<File> decompressZip(final ByteBuffer byteBuffer, final String path) {

        log.info("[ZIP] decompressing archive with {} name", FilenameUtils.getName(path));
        final List<File> files = new ArrayList<>();


        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(byteBuffer.array()))
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry() ) != null) {
                final String entryName = entry.getName();
                if (entryName.startsWith("__MACOSX/")) {
                    continue;
                }

                final String fileName = getFileName(entryName);
                final File newFile = new File(path + fileName);
                if (DS_STORE_FILENAME.equals(fileName)
                    || FilenameUtils.getExtension(fileName).equals("")) {
                    continue;
                }

                // create all non exists folders
                FileUtils.forceMkdirParent(newFile);

                final FileOutputStream fos = new FileOutputStream(newFile);

                writeToFileFromStream(fos, zis);
                files.add(newFile);
                log.info("[ZIP] decompress new file: {}", newFile.getAbsoluteFile());

                fos.close();
                zis.closeEntry();
            }
            log.info("[ZIP] Archive has been decompressed into {}", path);

        } catch (IOException ex) {
            log.error("[ZIP] exception happens while working with archive", ex);
            throw new RuntimeException(ex);
        }

        return files;
    }

    private static String getFileName(final String entryName) {
        final String[] array = entryName.split(FILE_SEPARATOR);
        if (array.length > 1) {
            return array[array.length - 1];
        }
        return entryName;
    }

    private static void writeToFileFromStream(
            final FileOutputStream fos, final ZipInputStream zis)
            throws IOException {
        int bytesRead = zis.read(BUFFER);
        while (bytesRead != -1) {
            fos.write(BUFFER, 0, bytesRead);
            bytesRead = zis.read(BUFFER);
        }
    }
}
