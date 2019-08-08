package io.cronica.api.pdfgenerator.component.aws;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import io.cronica.api.pdfgenerator.configuration.Beans;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Component
public class Repeater {

    private static final String AMAZON_S3_CLIENT_BEAN_NAME = "initAmazonS3";

    private static final int TRIALS = 3;

    private final Beans beans;

    private final GenericApplicationContext context;

    public <T, R> R apply(Function<T, R> func, T param) {
        final CountDownLatch countDownLatch = new CountDownLatch(TRIALS);
        final CountDownLatch countDownBeanRecreated = new CountDownLatch(TRIALS);

        while (true) {
            try {
                return func.apply(param);
            }
            catch (SdkClientException ex) {
                countDownLatch.countDown();
                log.info("[AWS S3] unable to reach file, trying again");

                if (countDownLatch.getCount() == 0) {
                    if (countDownBeanRecreated.getCount() == 0) {
                        log.error("[AWS S3] unable to reach file");
                        throw new RuntimeException(ex);
                    }
                    recreateAmazonS3Bean();
                    countDownBeanRecreated.countDown();
                }
            }
        }
    }

    public <T, U> void apply(BiConsumer<T, U> func, T param1, U param2) {
        final CountDownLatch countDownLatch = new CountDownLatch(TRIALS);
        final CountDownLatch countDownBeanRecreated = new CountDownLatch(TRIALS);

        while (true) {
            try {
                func.accept(param1, param2);
                break;
            }
            catch (SdkClientException ex) {
                countDownLatch.countDown();
                log.info("[AWS S3] unable to reach file, trying again");

                if (countDownLatch.getCount() == 0) {
                    if (countDownBeanRecreated.getCount() == 0) {
                        log.error("[AWS S3] unable to reach file");
                        throw new RuntimeException(ex);
                    }
                    recreateAmazonS3Bean();
                    countDownBeanRecreated.countDown();
                }
            }
        }
    }

    private void recreateAmazonS3Bean() {
        try {
            if (this.context.containsBean(AMAZON_S3_CLIENT_BEAN_NAME)) {
                this.context.removeBeanDefinition(AMAZON_S3_CLIENT_BEAN_NAME);
                this.context.registerBean(AMAZON_S3_CLIENT_BEAN_NAME, AmazonS3.class, this.beans::initAmazonS3);
            }
        }
        catch (Exception ex) {
            log.error("Exception while deploying TemplateRegistry contract", ex);
        }
    }
}
