FROM tomcat:9.0.0.M15-jre8

#RUN mkdir -p "/usr/local/tomcat/webapps/ROOT/WEB-INF/classes"
#COPY ./*.class /usr/local/tomcat/webapps/ROOT/WEB-INF/classes

RUN mkdir -p "/usr/local/tomcat/webapps/ROOT/WEB-INF/lib"
COPY ./target/TomcatSample-1.0-SNAPSHOT.jar /usr/local/tomcat/webapps/ROOT/WEB-INF/lib

COPY ./web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF