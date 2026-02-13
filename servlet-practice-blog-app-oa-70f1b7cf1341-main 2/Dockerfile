FROM tomcat:9.0-jdk11-temurin

ENV CATALINA_HOME /usr/local/tomcat

COPY target/blog-app.war $CATALINA_HOME/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
