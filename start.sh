#!/bin/bash

# Compile the application
mvn clean compile

# Execute the application
mvn exec:java -Dexec.mainClass="com.dkatalis.atm.Main"
