# scala-challenge

This is a practice of making a web RESTful service with AKKA, this work is modified from yangbajing's excellent example [1]. This toy project provides compressing and decompressing POST services that to user upload a log file and receive a compressed file, vice versa.

The compressing algorithm is based on the count of consecutive characters, for example:
aaaabbc => a4b2c1 
The algorithm is implemented in /src/main/scala/compressor, the compression related package 

## Run the webservice
```{r, engine='bash', count_lines}
./sbt
```

```{r, engine='bash', count_lines}
akka-action > runMain me.chienlu.akkaaction.restapi.App
```

## Test 
```{r, engine='bash', count_lines}
cd ~/scala-challenge/docs/scripts/restapi

./post-compress.sh
./post-decompress.sh
./post-decompress_wrong_format.sh
```

Reference
[1] http://git.oschina.net/yangbajing/akka-action
