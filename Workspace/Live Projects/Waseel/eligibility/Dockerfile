FROM openjdk:11.0.8-jdk
COPY ./target/*.jar /
ADD ./run.sh /run.sh
RUN chmod a+x /run.sh
EXPOSE 8080:8080
CMD /run.sh