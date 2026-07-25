FROM openjdk:11.0.8-jdk
ENV TZ=Asia/Riyadh
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY ./wallet/* /wallet/
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","app.jar"]