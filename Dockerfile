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
RUN mkdir IdeaProjects
RUN cd IdeaProjects
RUN mkdir SPE-Chess
RUN cd SPE-Chess
RUN mkdir target
RUN mkdir src
RUN cd src
RUN mkdir main
RUN cd main
RUN mkdir resources
RUN cd resources
RUN mkdir pictures
COPY ./target/SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar ./IdeaProjects/SPE-Chess/target/
COPY ./src/main/resources/pictures ./IdeaProjects/SPE-Chess/src/main/resources/pictures/
COPY javafx-sdk-11.0.2 javafx-sdk-11.0.2
WORKDIR ./IdeaProjects/SPE-Chess/
#CMD ["java", "-jar", "SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar"]
CMD ["java","--module-path","/javafx-sdk-11.0.2/lib","--add-modules","javafx.controls,javafx.fxml","-jar","SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar"]
#ENTRYPOINT java --module-path /javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -jar SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar -Dprism.verbose=true
#java --module-path /javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -jar SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar
#docker run -it --net=host -e DISPLAY -v /tmp/.X11-unix <image-name> bash
