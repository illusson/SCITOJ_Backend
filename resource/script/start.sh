#!/bin/bash

pwd=$(cd "$(dirname "$0")";pwd)

chown -R scitoj:scitoj $pwd/../

su - scitoj <<EOF

cd $pwd/../
nohup java -jar "$pwd/../SCITOJ_Backend.jar" "$*" >/dev/null 2>&1 &
echo "Application start successfully!"

EOF