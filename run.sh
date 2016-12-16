#!/usr/bin/env bash

javac -cp ~/.m2/repository/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar HelloWorld.java

docker build -t tomcat_sample .

docker run -p 8080:8080 tomcat_sample