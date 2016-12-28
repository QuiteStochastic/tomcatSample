FROM tomcat:9.0.0.M15-jre8


##copy relavent files and jars into the container
COPY ./web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF
COPY ./src/main/resources/persons.jsp /usr/local/tomcat/webapps/ROOT/WEB-INF
RUN mkdir -p "/usr/local/tomcat/webapps/ROOT/WEB-INF/lib"
COPY ./target/TomcatSample-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/tomcat/webapps/ROOT/WEB-INF/lib

COPY ./tomcat_key.p12 /etc/ssl
#COPY ./tomcat_cert.pem /etc/ssl

RUN $JAVA_HOME/bin/keytool -importkeystore -deststorepass changeit -destkeypass changeit -destkeystore $JAVA_HOME/lib/security/cacerts -srckeystore /etc/ssl/tomcat_key.p12 -srcstoretype PKCS12 -srcstorepass qwertyu

#RUN cat /etc/ssl/tomcat_cert.pem > $JAVA_HOME/lib/security/cacerts