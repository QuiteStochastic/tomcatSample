FROM tomcat:9.0.0.M15-jre8


##copy relavent files and jars into the container
COPY ./web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF
COPY ./src/main/resources/persons.jsp /usr/local/tomcat/webapps/ROOT/WEB-INF
RUN mkdir -p "/usr/local/tomcat/webapps/ROOT/WEB-INF/lib"
COPY ./target/TomcatSample-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/tomcat/webapps/ROOT/WEB-INF/lib

COPY ./tomcat.p12 /etc/ssl
COPY ./ca_cert.pem /etc/ssl
COPY ./tomcat_cert.pem /etc/ssl
COPY ./tomcat_key.pem /etc/ssl



RUN keytool -import -trustcacerts -alias TEST_ROOT_CERT -file /etc/ssl/ca_cert.pem -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -deststorepass changeit -noprompt




#RUN keytool -importkeystore -deststorepass changeit -destkeypass changeit -destkeystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -srckeystore /etc/ssl/tomcat.p12 -srcstoretype PKCS12 -srcstorepass qwertyu -noprompt


#RUN keytool -import -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -deststorepass changeit -srckeystore /etc/ssl/tomcat.p12 -srcstoretype PKCS12 -srcstorepass qwertyu -noprompt





#RUN keytool -import -alias TEST_TOMCAT_CERT -file /etc/ssl/tomcat_cert.pem -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -deststorepass changeit -noprompt
