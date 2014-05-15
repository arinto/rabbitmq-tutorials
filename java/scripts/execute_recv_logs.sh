#! /bin/bash

CP=.:../target/rabbitmq-sandbox-0.0.1-SNAPSHOT-jar-with-dependencies.jar

java -cp "$CP" sg.edu.smu.larc.research.main.ReceiveLogs
