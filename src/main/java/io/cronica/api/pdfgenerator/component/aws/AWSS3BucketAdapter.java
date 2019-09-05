package io.cronica.api.pdfgenerator.component.aws;

import io.reactivex.Flowable;

public interface AWSS3BucketAdapter {

    /**
     * Check whether exists file under specified key.
     *
     * @param key
     *          - key of the file
     * @return {@code true} if file exists, {@code false} otherwise
     */
    boolean fileExists(String key);

    /**
     * Download a file from AWs S3 Bucket by its key.
     *
     * @param key
     *          - the key of the file
     * @return file as array of bytes
     */
    byte[] downloadFile(String key);

    /**
     * Download all files which have specified prefix in their names.
     *
     * @param prefix
     *          - prefix of the files
     * @param pathToDirectory
     *          - path to directory where files should be downloaded
     */
    void downloadFilesWithPrefix(String prefix, String pathToDirectory);

    /**
     * Download all files which have specified prefix in their names.
     *
     * @param prefix
     *          - prefix of the files
     * @return
     *          - Flowable with files data
     */
    Flowable<byte[]> downloadFilesWithPrefix(String prefix);
}
