#################################
#### DOWNLOAD KAFKA BINARIES ####
#################################

# Download Kafka at https://kafka.apache.org/downloads

# Extract Kafka using 7Zip or WinRAR
# https://www.7-zip.org/

# Place at C:\kafka_2.12-2.0.0

# Install Java 8 JDK:
# http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

# Open a command line
# this should show JDK 8
java -version 

# Try out a Kafka command
bin\windows\kafka-topics.bat

# Edit your environment variables and add to PATH
C:\kafka_2.12-2.0.0\bin\windows

# Open a new terminal
# Try running the command from any directory:
kafka-topics.bat