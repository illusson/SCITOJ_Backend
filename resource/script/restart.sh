#!/bin/bash
pwd=$(cd "$(dirname "$0")";pwd)
sh $pwd/stop.sh "$*" && sh $pwd/start.sh