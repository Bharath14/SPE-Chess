FROM openjdk:11
COPY ./target/SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar ./
WORKDIR ./
CMD ["java", "-jar", "SPE-Chess-1.0-SNAPSHOT-jar-with-dependencies.jar"] 
#"--module-path",
#"/home/bharath/IdeaProjects/spemain/src/main/resources/com/example/javafx-sdk-11.0.2/lib",
# "--add-modules", "javafx.controls",",javafx.fxml"]
