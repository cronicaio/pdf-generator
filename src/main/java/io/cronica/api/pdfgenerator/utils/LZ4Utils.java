package io.cronica.api.pdfgenerator.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;

import java.io.*;

@Slf4j
public class LZ4Utils {

    /**
     * Decompress data, compressed using 'LZ4' algorithm.
     *
     * @param input
     *          - array of bytes to decompress
     * @return decompressed data
     */
    public static byte[] decompress(final byte[] input) {
        log.debug("[LZ4] size of data before decompression = {}", input.length);
        if (input.length == 0) {
            log.debug("[LZ4] empty array. Skip decompression.");
            return input;
        }

        final BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(input));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (FramedLZ4CompressorInputStream lz4In = new FramedLZ4CompressorInputStream(is)) {
            final byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = lz4In.read(buffer))) {
                baos.write(buffer, 0, n);
            }
            baos.close();

            final byte[] decompressedData = baos.toByteArray();
            log.debug("[LZ4] size of decompressed data = {}", decompressedData.length);
            return decompressedData;
        }
        catch (IOException ex) {
            log.error("[LZ4] exception while decompressing data", ex);
            throw new RuntimeException("Exception while decompressing data", ex);
        }
    }

}
