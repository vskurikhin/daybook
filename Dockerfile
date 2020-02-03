FROM openjdk:8-jre-alpine
MAINTAINER Victor N. Skurikhin

ENV _JAVA_OPTIONS="-Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true"
WORKDIR /
COPY build/libs/db-thorntail-thorntail.jar /

EXPOSE 18080

CMD [ "java", \
"-Djboss.as.management.blocking.timeout=600", \
"-jar", \
"/db-thorntail-thorntail.jar" ]
