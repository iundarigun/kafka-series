# uReplicator

Para rodar o uReplicator precisamos do controller e do worker:

```
$ java -jar uReplicator.jar startMirrorMakerController -helixClusterName clusterHelix -zookeeper localhost:2182 -port 9000

$ java -jar uReplicator.jar startMirrorMakerWorker --consumer.config config/consumer.properties --producer.config config/producer.properties --helix.config config/helix.properties 
```

## Replicar tópicos
Para indicar ao controller que queremos replicar um determinado tópico, devemos usar a API do controller:
```
curl -X POST -d '{"topic":"*", "numPartitions":"14"}' http://localhost:9000/topics
```

## Elementos
O uReplicator precisa do Zookeeper para funcionar, e usa o Apache Helix. Por baixo do panos ele usa o próprio MirrorMaker, que usam os arquivos `producer.properties` e `consumer.properties`. 

