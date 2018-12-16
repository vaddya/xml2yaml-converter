#!/usr/bin/env bash

PORT=8080
if [ "$#" -eq 1 ]; then
    PORT=$1
fi

docker run -p $PORT:8080 com.vaddya/xml2yaml
