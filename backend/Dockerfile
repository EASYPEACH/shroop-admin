FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/ shroopadmin-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE}  shroopadmin.jar
ENTRYPOINT ["java","-jar","/shroopadmin.jar"]
