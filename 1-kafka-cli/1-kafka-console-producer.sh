# Replace "kafka-console-producer" 
# by "kafka-console-producer.sh" or "kafka-console-producer.bat" based on your system # (or bin/kafka-console-producer.sh or bin\windows\kafka-console-producer.bat if you didn't setup PATH / Environment variables)

kafka-console-producer 

# producing
kafka-console-producer --broker-list 127.0.0.1:9092 --topic first_topic 
hello stephane
awesome course
learning kafka
just another message

# producing with properties
kafka-console-producer --broker-list 127.0.0.1:9092 --topic first_topic --producer-property acks=all
some message that is acked
just for fun
fun learning!

# producing to a non existing topic
kafka-console-producer --broker-list 127.0.0.1:9092 --topic new_topic
hello world!

# our new topic only has 1 partition
kafka-topics --zookeeper 127.0.0.1:2181 --list
kafka-topics --zookeeper 127.0.0.1:2181 --topic new_topic --describe

# edit config/server.properties
# num.partitions=3

# produce against a non existing topic again
kafka-console-producer --broker-list 127.0.0.1:9092 --topic new_topic_2
hello again!

# this time our topic has 3 partitions
kafka-topics --zookeeper 127.0.0.1:2181 --list
kafka-topics --zookeeper 127.0.0.1:2181 --topic new_topic_2 --describe

# overall, please create topics before producing to them!