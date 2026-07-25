FROM openjdk:11.0.8-jdk
ENV TZ=Asia/Riyadh
ARG JAR_FILE=target/*.jar
RUN mkdir opt/wallet
COPY ./wallet/* opt/wallet/
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
