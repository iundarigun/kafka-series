# Create Zookeeper and Kafka Directory
C:\kafka_2.12-2.0.0\data\zookeeper
C:\kafka_2.12-2.0.0\data\kafka

# Edit config\zookeeper.properties
# Using NotePad++ https://notepad-plus-plus.org/download/v7.5.8.html
# change line to 
# dataDir=C:/kafka_2.12-2.0.0/data/zookeeper

# start zookeeper (make sure nothing is running on port 2181)
zookeeper-server-start.bat config\zookeeper.properties

# Open a new command line (we leave zookeeper running in previous command line)

# Edit config\server.properties
# change line to 
# log.dirs=C:/kafka_2.12-2.0.0/data/kafka

# start Kafka
kafka-server-start.bat config\server.properties

# Kafka is running! 
# Keep the two command line windows opened