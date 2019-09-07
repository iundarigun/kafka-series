# Create a VM in your favourite cloud
# note the public or private IP (whichever is accessible from your computer)

# Download & Extract Kafka
wget http://apache.crihan.fr/dist/kafka/2.0.0/kafka_2.11-2.0.0.tgz
tar -xvf kafka_2.11-2.0.0.tgz

# install Java 8 (whichever way works for your system)
sudo yum install java-1.8.0-openjdk
java -version

# export environment variable if needed for dimishing Kafka RAM
export KAFKA_HEAP_OPTS="-Xmx256M -Xms128M"

# start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties
# start Kafka
bin/kafka-server-start.sh config/server.properties

# Kafka starts but is not accessible from your machine

# edit config/server.properties
# add advertised.listeners=PLAINTEXT://<MACHINE-IP-HERE>:9092
