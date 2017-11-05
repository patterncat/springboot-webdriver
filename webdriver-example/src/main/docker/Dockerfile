FROM registry.cn-hangzhou.aliyuncs.com/patterncat/firefox:ubuntu1404
VOLUME /tmp
ADD app.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080

ADD start.sh /usr/bin/start.sh
RUN chmod +x /usr/bin/start.sh

ENTRYPOINT ["/usr/bin/start.sh"]