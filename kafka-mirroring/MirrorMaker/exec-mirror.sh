#!/bin/bash

export CLASSPATH=/opt/message-handler.jar
/opt/kafka/bin/kafka-mirror-maker.sh --consumer.config /opt/mirror-maker/consumer.properties --producer.config /opt/mirror-maker/producer.properties --new.consumer --num.streams=$NUM_STREAMS --whitelist "$WHITELIST" --offset.commit.interval.ms=$COMMIT_INTERVAL --message.handler com.slavirok.RenameTopicHandler --message.handler.args "$HANDLER_ARGS"