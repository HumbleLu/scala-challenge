#!/usr/bin/env bash
curl -v -XPOST -H 'content-type: application/json' \
    -d '{"inputFile":"/Users/chienlu/test_compressed.txt"}' \
    http://localhost:3333/api/decompress
echo
