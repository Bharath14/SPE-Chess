FROM adoptopenjdk/openjdk11:alpine
RUN apt update && apt install libgtk-3-0 libglu1-mesa -y && apt update
#RUN apk update && apk add libx11 mesa-gl gtk+3.0 mesa-dri-swrast mesa-demos && apk update
COPY ./target/SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar ./
COPY javafx-sdk-11.0.2 javafx-sdk-11.0.2
WORKDIR ./
#CMD ["java","--module-path","/javafx-sdk-11.0.2/lib","--add-modules","javafx.controls,javafx.fxml","-jar","SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar"]
ENTRYPOINT java --module-path /javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -jar SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar -Dprism.verbose=true
