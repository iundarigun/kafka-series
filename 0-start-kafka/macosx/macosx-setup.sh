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

# Try brew
brew

# Install brew if needed:
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

# Install Java 8 if needed
brew tap caskroom/versions
brew cask install java8

# Verify Java 8
java -version

# Try out a Kafka command
bin/kafka-topics.sh

# Edit .bash_profile
nano ~/.bash_profile
# Add PATH="$PATH:/your/path/to/your/kafka/bin"

# Open a new terminal
# Try running the command from any directory:
kafka-topics.sh

####################
#### USING BREW ####
####################

# Edit .bash_profile
nano ~/.bash_profile
# remove the previously added line PATH="$PATH:/your/path/to/your/kafka/bin"

# Install kafka using brew
brew install kafka

# Open a new terminal
# Try a kafka command:
kafka-topics