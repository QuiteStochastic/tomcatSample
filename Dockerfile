FROM tomcat:9.0.0.M15-jre8


##copy app server files and jars into the container
COPY ./web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF
COPY ./src/main/resources/persons.jsp /usr/local/tomcat/webapps/ROOT/WEB-INF
RUN mkdir -p "/usr/local/tomcat/webapps/ROOT/WEB-INF/lib"
COPY ./target/TomcatSample-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/tomcat/webapps/ROOT/WEB-INF/lib
##this file overrides the default server.xml.  needed for ssl
COPY ./server.xml $CATALINA_HOME/conf


##this file is passed to Catalina opts for ssl
COPY ./tomcat.p12 /etc/ssl

##this is given to keytools to import for ssl
COPY ./ca_cert.pem /etc/ssl

##these two files are used in server.xml for ssl
COPY ./tomcat_cert.pem /etc/ssl
COPY ./tomcat_key.pem /etc/ssl


RUN touch $CATALINA_HOME/bin/setenv.sh;chmod 755 $CATALINA_HOME/bin/setenv.sh
RUN echo "CATALINA_OPTS="-Djavax.net.ssl.keyStoreType=pkcs12"\ "-Djavax.net.ssl.keyStore=/etc/ssl/tomcat.p12"\ "-Djavax.net.ssl.keyStorePassword=qwertyu"" > $CATALINA_HOME/bin/setenv.sh


RUN keytool -import -trustcacerts -alias TEST_ROOT_CERT -file /etc/ssl/ca_cert.pem -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -deststorepass changeit -noprompt
