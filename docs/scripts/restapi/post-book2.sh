#!/usr/bin/env bash
curl -v -XPOST -H 'content-type: application/json' \
    -d '{"inputFile":"/Users/chienlu/test.txt"}' \
    http://localhost:3333/api/compress
echo
