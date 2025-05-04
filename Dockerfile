FROM openjdk:21
 
WORKDIR /app
COPY target/veterinaria-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet_dba1 /app/oracle_wallet/
EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=docker", "-Doracle.net.tns_admin=/app/oracle_wallet", "-jar", "app.jar"]