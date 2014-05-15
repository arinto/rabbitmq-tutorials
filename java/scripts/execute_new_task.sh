#! /bin/bash

CP=.:../target/rabbitmq-sandbox-0.0.1-SNAPSHOT-jar-with-dependencies.jar
ARGUMENT=""
for var in "$@";do
    ARGUMENT="$ARGUMENT $var"
done

java -cp "$CP" sg.edu.smu.larc.research.main.NewTask $ARGUMENT
