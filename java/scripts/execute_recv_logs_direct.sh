#! /bin/bash

CP=.:../target/rabbitmq-tutorials-0.0.1-SNAPSHOT-jar-with-dependencies.jar

ARGUMENT=""
for var in "$@";do
    ARGUMENT="$ARGUMENT $var"
done

java -cp "$CP" ReceiveLogsDirect $ARGUMENT
