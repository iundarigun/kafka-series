# copy server.properties into
# server0.properties and 
# server1.properties and 
# server2.properties

# then edit each file:
# server1.properties
# broker.id=1
# listeners=PLAINTEXT://:9093
# logs.dir=/path/to/data/kafka1

# server2.properties
# broker.id=2
# listeners=PLAINTEXT://:9094
# logs.dir=/path/to/data/kafka2

# then start kafka (3 different terminals)
kafka-server-start config/server.properties
kafka-server-start config/server2.properties
kafka-server-start config/server3.properties

# you can create a topic with RF of 3!
kafka-topics --zookeeper 127.0.0.1:2181 --create --topic test --replication-factor 3 --partitions 3