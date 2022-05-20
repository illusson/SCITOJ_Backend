#!/bin/bash

PID=$(ps -ef | grep SCITOJ_Backend.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]; then
    echo "Application is already stopped!"
else
    echo kill -9 $PID
    kill -9 $PID
    echo "Application stop successfully!"
fi
