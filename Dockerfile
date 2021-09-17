FROM openjdk:15
VOLUME /tmp
ADD ./target/app-autenticacion-0.0.1-SNAPSHOT.jar autenticacion.jar
ENTRYPOINT ["java","-jar","/autenticacion.jar"]