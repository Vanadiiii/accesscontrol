FROM openjdk:11
MAINTAINER IvanMatveev
COPY . /accesscontrol
WORKDIR /accesscontrol
CMD ["gradle", "clean", "build"]
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /accesscontrol.jar
ENTRYPOINT ["java", "-jar", "/accesscontrol.jar"]
