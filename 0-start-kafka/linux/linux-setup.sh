#################################
#### DOWNLOAD KAFKA BINARIES ####
#################################

# Download Kafka at https://kafka.apache.org/downloads

# Extract Kafka
tar -xvf kafka_2.12-2.0.0.tgz

# Open the Kafka directory
cd kafka_2.12-2.0.0

# this should show JDK 8
java -version 

# Otherwise Install Java JDK 8 (must be 8!)
sudo apt install openjdk-8-jdk

# Verify Java 8
java -version

# Try out a Kafka command
bin/kafka-topics.sh

# Edit .profile
nano ~/.profile
# Add PATH="$PATH:/your/path/to/your/kafka/bin"
nano ~/.bashrc
# Add at the bottom:
# . ~/.profile

# Open a new terminal
# Try running the command from any directory:
kafka-topics.sh