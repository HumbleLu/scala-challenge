#!/usr/bin/env bash
pwd=`pwd`
pwd+="/test_compressed_wrong_format.txt"

curl -v -XPOST -H 'content-type: application/json' \
    -d '{"inputFile":"'"$pwd"'"}' \
    http://localhost:3333/api/decompress
echo
