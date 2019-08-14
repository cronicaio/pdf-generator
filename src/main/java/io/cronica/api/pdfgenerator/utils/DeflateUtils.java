package io.cronica.api.pdfgenerator.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

@Slf4j
public class DeflateUtils {

    /**
     * Decompress data, compressed using 'DEFLATE' algorithm.
     *
     * @param input
     *          - array of bytes to decompress
     * @return decompressed data
     */
    public static byte[] decompress(final byte[] input) {
        final InputStream in = new InflaterInputStream(new ByteArrayInputStream(input));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final byte[] buffer = new byte[8192];
            int len;
            while ( (len = in.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            final byte[] decompressedData = baos.toByteArray();
            log.debug("[DEFLATER] size of decompressed data = {}", decompressedData.length);
            return decompressedData;
        }
        catch (IOException ex) {
            log.error("[DEFLATER] exception while decompressing data", ex);
            throw new RuntimeException("Exception while decompressing data", ex);
        }
    }

}
