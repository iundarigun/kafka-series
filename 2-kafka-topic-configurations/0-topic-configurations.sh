# Replace "kafka-topics" 
# by "kafka-topics.sh" or "kafka-topics.bat" based on your system # (or bin/kafka-topics.sh or bin\windows\kafka-topics.bat if you didn't setup PATH / Environment variables)

# list topics
kafka-topics --zookeeper 127.0.0.1:2181 --list

# create a topic that we'll configure
kafka-topics --zookeeper 127.0.0.1:2181 --create --topic configured-topic --partitions 3 --replication-factor 1

# look for existing configurations
kafka-topics --zookeeper 127.0.0.1:2181 --describe --topic configured-topic

# documentation of kafka-configs command
kafka-configs

# Describe configs for the topic with the command
kafka-configs --zookeeper 127.0.0.1:2181 --entity-type topics --entity-name configured-topic --describe

# add a config to our topic
kafka-configs --zookeeper 127.0.0.1:2181 --entity-type topics --entity-name configured-topic --add-config min.insync.replicas=2 --alter

# Describe configs using kafka-configs
kafka-configs --zookeeper 127.0.0.1:2181 --entity-type topics --entity-name configured-topic --describe

# Describe configs using kafka-topics
kafka-topics --zookeeper 127.0.0.1:2181 --describe --topic configured-topic

# Delete a config
kafka-configs --zookeeper 127.0.0.1:2181 --entity-type topics --entity-name configured-topic --delete-config min.insync.replicas --alter

# ensure the config has been deleted
kafka-topics --zookeeper 127.0.0.1:2181 --describe --topic configured-topic