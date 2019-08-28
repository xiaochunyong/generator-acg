FROM xxx.xxx.xxx/openjdk:8-jdk-alpine
RUN echo "Asia/Shanghai" > /etc/timezone
#RUN dpkg-reconfigure -f noninteractive tzdata
VOLUME /tmp
ADD ./app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=production","-jar","/app.jar"]