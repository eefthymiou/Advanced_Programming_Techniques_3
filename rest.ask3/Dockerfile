FROM adoptopenjdk/openjdk13-openj9:latest
MAINTAINER ece.upatras.gr
COPY deploy/rest.ask3-0.0.1-SNAPSHOT-exec.jar /opt/rest.ask3/
WORKDIR /opt/rest.ask3/
CMD ["java", "-jar", "/opt/rest.ask3/rest.ask3-0.0.1-SNAPSHOT-exec.jar"]
EXPOSE 8888