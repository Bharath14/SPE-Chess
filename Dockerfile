FROM ubuntu:latest
RUN apt-get -y update
RUN apt-get -y install openjdk-11-jdk
RUN apt-get -y install xauth
#FROM adoptopenjdk/openjdk11:alpine
RUN apt update && apt install libgtk-3-0 libglu1-mesa -y && apt update
#RUN apt-get install -y xdg-utils
#RUN apt-get install libfile-mimeinfo-perl
#RUN apt-get install desktop-file-utils
#RUN apt-get -y install firefox

EXPOSE 8887
#RUN apk update && apk add libx11 mesa-gl gtk+3.0 mesa-dri-swrast mesa-demos && apk update
COPY ./target/SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar ./
COPY ./src/main/java/gui/pictures/BlackBishop.png ./
COPY javafx-sdk-11.0.2 javafx-sdk-11.0.2
WORKDIR ./
#CMD ["java", "-jar", "SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar"]
#CMD ["java","--module-path","/javafx-sdk-11.0.2/lib","--add-modules","javafx.controls,javafx.fxml","-jar","SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar"]
ENTRYPOINT java --module-path /javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -jar SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar -Dprism.verbose=true
#java --module-path /javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -jar SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar