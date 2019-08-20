package io.cronica.api.pdfgenerator.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        log.info("[DEFLATE] size of data before decompression = {}", input.length);
        if (input.length == 0) {
            log.debug("[DEFLATE] empty array. Skip decompression.");
            return input;
        }

        final BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(input));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final FramedLZ4CompressorInputStream lz4In = new FramedLZ4CompressorInputStream(is);
            final byte[] buffer = new byte[1025];       // odd number to prevent division by zero
            int n = 0;
            while (-1 != (n = lz4In.read(buffer))) {
                baos.write(buffer, 0, n);
            }
            lz4In.close();
            is.close();
            baos.close();

            final byte[] decompressedData = baos.toByteArray();
            log.info("[DEFLATE] size of decompressed data = {}", decompressedData.length);
            return decompressedData;
        }
        catch (IOException ex) {
            log.error("[DEFLATE] exception while decompressing data", ex);
            throw new RuntimeException("Exception while decompressing data", ex);
        }
    }

}
