# scala-challenge

This is a practice of making a web RESTful service with AKKA, this work is modified from yangbajing's excellent example [1]

## Run the webservice
```{r, engine='bash', count_lines}
./sbt
```

```{r, engine='bash', count_lines}
runMain me.chienlu.akkaaction.restapi.App
```

## Test 
```{r, engine='bash', count_lines}
cd ~/scala-challenge/docs/scripts/restapi

./post-compress.sh
./post-decompress.sh
```

Reference
[1] http://git.oschina.net/yangbajing/akka-action
