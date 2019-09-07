#!/bin/bash

# create our topic with appropriate configs
kafka-topics --zookeeper 127.0.0.1:2181 --create --topic employee-salary --partitions 1 --replication-factor 1 --config cleanup.policy=compact --config min.cleanable.dirty.ratio=0.001 --config segment.ms=5000

# Describe Topic Configs
kafka-topics --zookeeper 127.0.0.1:2181 --describe --topic employee-salary

# in a new tab, we start a consumer
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic employee-salary --from-beginning --property print.key=true --property key.separator=,

# we start pushing data to the topic
kafka-console-producer --broker-list 127.0.0.1:9092 --topic employee-salary --property parse.key=true --property key.separator=,
