FROM centos:centos7
MAINTAINER liuge

RUN yum update -y
RUN yum install -y java-1.8.0-openjdk
ADD dingtalk-gateway.jar /
EXPOSE 8080
ENTRYPOINT ["java","-jar","/dingtalk-gateway.jar"]
