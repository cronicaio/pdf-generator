package io.cronica.api.pdfgenerator.component.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import io.cronica.api.pdfgenerator.configuration.AWSProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AWSS3BucketAdapterImpl implements AWSS3BucketAdapter {

    private final AmazonS3 amazonS3;

    private final AWSProperties awsProperties;

    /**
     * @see AWSS3BucketAdapter#fileExists(String) 
     */
    @Override
    public boolean fileExists(final String key) {
        log.debug("[AWS S3] check whether exists file under '{}' key", key);
        final boolean exists = this.amazonS3.doesObjectExist(this.awsProperties.getS3BucketName(), key);
        if (exists) {
            log.debug("[AWS S3] object under '{}' key is found", key);
        }
        else {
            log.debug("[AWS S3] object under '{}' key does not exists", key);
        }
        return exists;
    }

    /**
     * @see AWSS3BucketAdapter#downloadFile(String) 
     */
    @Override
    public byte[] downloadFile(final String key) {
        log.info("[AWS S3] downloading file under '{}' key", key);
        try (S3Object s3Object = this.amazonS3.getObject(this.awsProperties.getS3BucketName(), key)) {
            log.info("[AWS S3] file under '{}' key has been downloaded", key);
            return IOUtils.toByteArray(s3Object.getObjectContent());
        }
        catch (IOException ex) {
            log.error("[AWS S3] exception while converting S3ObjectInputStream to byte array", ex);
            throw new RuntimeException("Exception while converting S3ObjectInputStream to byte array", ex);
        }
    }

    /**
     * @see AWSS3BucketAdapter#downloadFilesWithPrefix(String, String)
     */
    @Override
    public void downloadFilesWithPrefix(final String prefix, final String pathToDirectory) {
        log.info("[AWS S3] download files with '{}' prefix", prefix);
        final ObjectListing objectListing = this.amazonS3.listObjects(this.awsProperties.getS3BucketName(), prefix);
        final List<S3ObjectSummary> objects = findAllObjects(objectListing);
        try {
            FileUtils.forceMkdir(new File(pathToDirectory));
            for (S3ObjectSummary object : objects) {
                final S3Object s3Object = this.amazonS3.getObject(this.awsProperties.getS3BucketName(), object.getKey());
                final String fileName = generateName(prefix, s3Object);
                downloadFile(pathToDirectory + fileName, s3Object);
                s3Object.close();
            }
            log.info("[AWS S3] {} files downloaded to '{}' directory", objects.size(), pathToDirectory);
        }
        catch (IOException ex) {
            log.error("[AWS S3] unable to create directory/file or close resource", ex);
            throw new RuntimeException("Unable to create directory/file or close resource", ex);
        }
    }

    private List<S3ObjectSummary> findAllObjects(ObjectListing objectListing) {
        final List<S3ObjectSummary> objects = objectListing.getObjectSummaries();
        while (objectListing.getObjectSummaries().size() == 1000) {
            objectListing = this.amazonS3.listNextBatchOfObjects(objectListing);
            objects.addAll(objectListing.getObjectSummaries());
        }
        return objects;
    }

    private String generateName(final String prefix, final S3Object s3Object) {
        final int lengthOfPrefix = s3Object.getKey().indexOf(prefix) + prefix.length();
        return StringUtils.substring(s3Object.getKey(), lengthOfPrefix);
    }

    private void downloadFile(final String fileName, final S3Object s3Object) throws IOException {
        final File file = new File(fileName);
        if ( !file.exists() ) {
            FileUtils.touch(file);
            final S3ObjectInputStream inputStream = s3Object.getObjectContent();
            FileUtils.copyInputStreamToFile(inputStream, file);
            inputStream.close();
        }
    }
}
