#!/usr/bin/env bash
pwd=`pwd`
pwd+="/test.txt"

curl -v -XPOST -H 'content-type: application/json' \
    -d '{"inputFile": "'"$pwd"'"}' \
    http://localhost:3333/api/compress
echo 
