# Build Java project within gradle
FROM gradle:5.6.0-jdk11

USER root

ADD . ./

RUN gradle build

# Copy compiled Java application and run within Wkhtmltopdf container with Java
FROM cronicaio/wkhtmltopdf:0.12.5

RUN mkdir ./app/

COPY --from=0 /home/gradle/build/libs/pdf-generator-1.0.0.jar ./app/pdf-generator.jar
COPY --from=0 /home/gradle/template/fonts ./template/fonts
COPY --from=0 /home/gradle/template/footer ./template/footer

CMD java -jar app/pdf-generator.jar --port=80

EXPOSE 80