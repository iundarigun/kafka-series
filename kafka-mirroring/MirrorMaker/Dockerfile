FROM openjdk:8-jre-alpine

RUN wget https://downloads.apache.org/kafka/2.5.0/kafka_2.12-2.5.0.tgz && \
    tar -xvzf kafka_2.12-*.tgz && \
    mv kafka*/ /opt/kafka

RUN apk update &&  \
    apk add bash

COPY *.properties /opt/mirror-maker/

COPY *.jar /opt/message-handler.jar

ENV WHITELIST="DEVCAVE.EXAMPLE.MIRRORMAKER" \
    NUM_STREAMS=2 \
    HANDLER_ARGS="DEVCAVE.EXAMPLE.MIRRORMAKER,DEVCAVE.EXAMPLE.MIRRORMAKER.ALTERNATIVE" \
    COMMIT_INTERVAL=5000

COPY exec-mirror.sh /opt/exec-mirror.sh

RUN chmod +x /opt/exec-mirror.sh

ENTRYPOINT [ "sh", "-c", "/opt/exec-mirror.sh"]
