FROM openjdk:11.0.15
COPY ./target/SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar ./
COPY javafx-sdk-11.0.2 ./
WORKDIR ./
CMD ["java","--module-path ./javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml","-jar","SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar"]
