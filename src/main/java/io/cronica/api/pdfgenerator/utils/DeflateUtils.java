package io.cronica.api.pdfgenerator.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;

import java.io.*;

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

        final InputStream is = new ByteArrayInputStream(input);
        final BufferedInputStream bis = new BufferedInputStream(is);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final DeflateCompressorInputStream defIn = new DeflateCompressorInputStream(bis);
            final byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = defIn.read(buffer))) {
                baos.write(buffer, 0, n);
            }
            defIn.close();
            bis.close();
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
