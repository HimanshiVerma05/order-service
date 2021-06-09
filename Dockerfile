FROM java:8-jdk
RUN mkdir /app
WORKDIR /app
COPY target/order-0.0.1-SNAPSHOT.jar /app
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "order-0.0.1-SNAPSHOT.jar"]

