package io.cronica.api.pdfgenerator.component.kafka.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface GeneratePdfStream {

    String REQUEST = "generate-pdf";
    String RESPONSE = "generate-pdf-reply";

    @Input(REQUEST)
    SubscribableChannel generatePdfRequest();

    @Output(RESPONSE)
    MessageChannel broadcastPdfGenerationResponse();

}
