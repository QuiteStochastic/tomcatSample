FROM tomcat:9.0.0.M15-jre8


##copy relavent files and jars into the container
COPY ./web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF
COPY ./src/main/resources/persons.jsp /usr/local/tomcat/webapps/ROOT/WEB-INF
RUN mkdir -p "/usr/local/tomcat/webapps/ROOT/WEB-INF/lib"
COPY ./target/TomcatSample-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/tomcat/webapps/ROOT/WEB-INF/lib


RUN /usr/java/latest/jre/bin/keytool -importkeystore -destkeystore /usr/java/jdk1.8.0_112/jre/lib/security/keys.keystore -srckeystore key.p12 -srcstoretype PKCS12