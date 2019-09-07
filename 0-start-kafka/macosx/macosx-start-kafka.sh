# make a zookeeper data directory
mkdir data
mkdir data/zookeeper

# Edit config/zookeeper.properties
# change line to 
# dataDir=/your/path/to/data/zookeeper

# start zookeeper (make sure nothing is running on port 2181)
zookeeper-server-start config/zookeeper.properties

# Open a new terminal (we leave zookeeper running in previous terminal)

# create Kafka data directory
mkdir data/kafka
# Edit config/server.properties
# change line to 
# log.dirs=/your/path/to/data/kafka

# start Kafka
kafka-server-start config/server.properties

# Kafka is running! 
# Keep the two terminal windows opened