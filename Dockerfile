# Build Java project within gradle
FROM gradle:5.6.0-jdk11

USER root

ADD . ./

RUN gradle build

# Copy compiled Java application and run within Ubuntu Container with Java
FROM ubuntu:18.04

RUN mkdir ./app/

COPY --from=0 /home/gradle/build/libs/pdf-generator-1.0.0.jar ./app/pdf-generator.jar
COPY --from=0 /home/gradle/template/fonts ./template/fonts
COPY --from=0 /home/gradle/template/footer ./template/footer

RUN apt-get update
RUN apt-get upgrade -y
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y build-essential xorg libssl-dev libxrender-dev wget xfonts-75dpi libssl1.0.0
RUN wget http://se.archive.ubuntu.com/ubuntu/pool/main/libp/libpng/libpng12-0_1.2.54-1ubuntu1_amd64.deb
RUN dpkg -i libpng12-0_1.2.54-1ubuntu1_amd64.deb

# Install Java
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y software-properties-common
RUN DEBIAN_FRONTEND=noninteractive add-apt-repository ppa:openjdk-r/ppa
RUN DEBIAN_FRONTEND=noninteractive apt-get update
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install openjdk-11-jdk
RUN DEBIAN_FRONTEND=noninteractive update-alternatives --config java

# Set Up encoding
RUN apt-get clean && apt-get update && apt-get install -y locales
RUN locale-gen en_US.UTF-8
ENV LC_ALL en_US.UTF-8

# Install 'wkhtmltopdf' utility
RUN cd ~
RUN wget https://downloads.wkhtmltopdf.org/0.12/0.12.5/wkhtmltox_0.12.5-1.bionic_amd64.deb
RUN dpkg -i wkhtmltox_0.12.5-1.bionic_amd64.deb

CMD java -jar app/pdf-generator.jar --port=80

EXPOSE 80