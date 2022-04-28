FROM openjdk:11
COPY ./target/SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar ./
ADD javafx-sdk-11.0.2 ./
WORKDIR ./
CMD ["java", "-jar", "SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar --module-path javafx-sdk-11.0.2/lib --add-modules javafx.controls ,javafx.fxml"]
