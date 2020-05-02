# Kafka

## Diferença com filas

O protocolo AMQP:

![AMQP](images/amqp-about.png)

Artigo comparando RabbitMQ: 
- https://itnext.io/kafka-vs-rabbitmq-f5abc02e3912

## Conceitos sobre Kafka

### Tópicos

Topic: nome identificativo

Partitions: Um tópico tem n partições que sõ definidas na criação do tópico e esse número pode ser modificado depois. As partitions tem nome de `Partition` + número sequencial, começando por zero. Se tiver três partitions, teremos `Partition 0`, `Partition 1` e `Partition 2`.

Mensagens: Cada mensagem que é postada no tópico vai parar numa partição. Ele recebe uma numeração sequencial dentro do tópico chamada de offset. Então, para definir uma mensagem precisamos de `Topic name` + `Partition number` + `offset`.

Importante: A ordem das mensagens (offset) é garantida dentro da partição, mas não entre partições

![Topics](images/topics.png)

Por padrão, as mensagens são mantidas por 7 dias, mas é configurável.

### Clusters

Os clusters são formados por `brokers`, que basicamente são servidores ou instâncias, também recebem um identifidor numérico, mas que pode ser aletório.

Os tópicos e as partições são espalhados pelos `brokers` sem uma ordem específica:

![Cluster](images/cluster.png)

### Replicação

A replicação consiste em replicar as partições dos tópicos em outros brokers. O padrão de replicação é entre dois e três replicações. 

Cada partition tem um broker líder. Só é feita escrita de mensagens nesse broker, e o cluster cuida da replicação para o resto, chamada de ISR (In-sync replica).

![Replication](images/replication.png)

Os tópicos só podem ter um fator de replicação menor ou igual ao número de brokers

### Producers

Os producers sabem automaticamente sem ter que programar nada onde vão escrever - Broker e partition. Se não especificar nada na hora de mandar a mensagem, cada uma será postada num broker e partition diferente via round robin.

![Producer](images/producers.png)


Existem três tipos  de confirmação:
- acks=0 : Não é esperada confirmação. Pode perder mensagens se o broker estiver offline.
- acks=1 : Espera a confirmação do líder. Em alguns casos, pode perder mensagens.
- acks=all : é esperada a confirmação dos líderes e das réplicas. 

Podemos mandar as mensagens com uma key, que pode ser qualquer coisa. Nesse caso é garantido que cairá na mesma partição, preservando a ordenação das mensagens, mas a partição não é escolhida diretamente. **Isso é verdade enquanto o número de partições não mudar**.

`Nota`: Quando selecionamos o `acks=all`, precisamos levar em consideração o fator de replicação e o mínimo de ISR. Então, se tiver um fator de replicação de 3, e a configuração de mínimo de ISR é 2, só poderemos ter um broker offline quando o produtor seja `acks=all`.

Os produtores nas versões maiores da 2.1, vão retentar reenviar a mensagem bilhões de vezes, com um timeout de 100 ms entre tentativa. Esses parâmetros são configuráveis. Então, pelos dados, se houver algum erro, a retentativa vai tomar muito tempo, mas existe outro parâmetro que é o `producer timeout` que evita isso

#### Imdepotência

Nas versões iniciais do Kafka poderia acontecer o seguinte:

![Imdepotencia](images/imdepotencia.png)

Após a versão 0.11, o kafka gera um id de mensagem e sabe lidar com cenârios de duplicidade. É uma opção que precisa/pode ser ativada no producer.

### Compressão

As mensagens podem ser agrupadas e comprimidas para optimização de fluxos. Quanto mais mensagens, maior o ganho de performance:

![Compressao](images/messageCompression.png)

Para fazer isso precisamos brincar nas configurações dos producers para introduzir latência na produção, de forma que as mensagens possam ser agrupadas.

### Consumers

Igual que os producers, os consumers sabem exatamente em que broker/partition vão ler as mensagens. Cada consumidor pode ler de mais de uma partition mas a ordem não é garantida entre partições.

![Consumers](images/consumers.png)

Os consumers são agrupados por grupos. Cada grupo de consumidores tem um nome que pode ser explicitado. O grupo de consumidores vai distribuir a leitura entre os diferentes consumidores disponíveis.

![Consumers](images/consumergroups.png)

Podemos ter mais consumidores que partitions, mas nesse caso o excedente fica inativo esperando por se algum consumidor cair. Por isso é importante definir o número de partitions no começo.

### Consumers offsets

O kafka guarda os offsets para cada grupo de consumidores, e isso é guardado num tópico chamado `__consummer_offsets`. Normalmente o consumidor já faz isso e não precisa implementar. 

Quando vamos commitar o offset, precisamos indicar qual semântica queremos usar. Temos três tipos:
- At most once: Tão cedo quando recebemos a mensagem, commitamos o offset. Pode gerar perda de mensagem caso ao processar a mensagem aconteça um erro inesperado.
- At least once: Após a leitura das mensagens, commitamos. Pode gerar leituras duplicadas caso o consumidor morrer antes de commitar. Por isso precisamos que nosso sistema consumidor seja imdepotente.
- Exacly once: É só de kafka to kafka

### Kafka broker discovery

Cada broker é um bootstrap server. Isso significa que conectando em um broker, podemos acessar ao resto do cluster.

Isso acontecer porque o broker tem conhecimento de todos os brokers, tópicos e partitions mesmo não tendo mensagens deles. 

Quando um consumidor conecta num broker, solicita metadados. Assim, quando precisa ler determinada partition, só precisa verificar em que broker está e connectar nesse broker.

![Broker discovery](images/brokerdiscovery.png)

### Zookeper

O zookeeper é o encarregado de cuidar do cluster de kafka. Escolhe o broker leader e mantém o kafka atualizado sobre novos tópicos, estado do brokers, etc

Kafka não funciona sem zookeeper. Para um cluster de Kafka teremos um cluster de zookeeper, e número de instâncias no cluster de zookeeper é sempre ímpar. Em local um zookeeper atende, mas em prod serão 3, 5, etc

![Zookeeper](images/zookeeper.png)

## Resumindo

![Resumo](images/resumo.png)


### Diferenças entre RabbitMQ e Kakfa 

https://itnext.io/kafka-vs-rabbitmq-f5abc02e3912


---

## Comandos CLI

### Iniciando kafka

Primeiro, startar o zookeeper:
```
$ zookeeper-server-start.sh /opt/kafka_2.12-2.3.0/config/zookeeper.properties 
```
O arquivo properties tem a linha `dataDir=/opt/kafka_2.12-2.3.0/data/zookeeper`

```
$ kafka-server-start.sh /opt/kafka_2.12-2.3.0/config/server.properties
```
O arquivo properties tem a linha `log.dirs=/opt/kafka_2.12-2.3.0/data/kafka`

Topics: Precisamos especificar o zookeeper quando for usar o CLI para criação de Kafka topics. 
```
$ kafka-topics.sh --zookeeper localhost:2181
```
Nas novas versões, essa opção está depreciada:
```
$ kafka-topics.sh --bootstrap-server localhost:9092
```
### create
```
$ kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1
``` 

### list
```
$ kafka-topics.sh --bootstrap-server localhost:9092 --list
``` 
### Descrição do tópico
```
$ kafka-topics.sh --bootstrap-server localhost:9092 --topic first-topic --describe
``` 
### Delete
```
$ kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --delete
``` 
Só vai funcionar se a opção delete topic enable está true, o que por padrão é assim.

### Produce message
```
$ kafka-console-producer.sh --broker-list localhost:9092 --topic first_topic
```

### Producer com acks
```
$ kafka-console-producer.sh --broker-list localhost:9092 --topic first_topic --producer-property acks=all
```
Se o topico não existir, vai funcionar do mesmo jeito, pois vai ser criado. Vai usar a configuração de partitions e replication-factor configurado no `server.properties`. 

Uma boa prática é não deixar que o tópico seja criado automaticamente e criá-lo manualmente antes de produzir mensagens nele.

### Producer com key
```
$ kafka-console-producer --broker-list localhost:9092 --topic first_topic --property parse.key=true --property key.separator=,
> key,value
> another key,another value
```

### Consumers

```
$ kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic
```
Este consumidor só vai ler as mensagens novas. Para ler  mensagens antigas:
```
$ kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --from-beginning
```

### With keys

```
$ kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --from-beginning --property print.key=true --property key.separator=,
```

### Grupos
```
$ kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --group firstconsumer
```
Se usamos a opção `--from-beginning` vamos ler as mensagem após commit do offset, aquelas mensagens que não foram consumidas pelo grupo.

```
$ kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```

```
$ kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group firstconsumer
```

### Reset offset
```
$ kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group firstconsumer --reset-offsets --to-earliest --topic first_topic --execute
```

## Configurações e informações avançadas

### Configurando tópicos
Há dois pontos importantes na hora de criar um tópico:
- Número de partições
- Factor de replicação
Qualquer mudança nestes pontos, pode afetar a performance e o funcionamento. Por exemplo, se aumentar o número de partições, vai mudar nosso `key partition` e a ordem durante aquele período de transição não será garantida.

#### Número de partições
- Cada partição tem um throughput de x MB/s
- Mais partições implica melhor paralelismo, melhor throughput, mais consumidores
- Mais partições, porém, implica mais arquivos abertos no Kafka e mais trabalho para o Zookeeper
- Em casos de uso normal, um número de partições 2x o número de brokers pode ser suficiente, mas se precisar escalar o consumo, pode não ser suficiente
- É aceitado que cada tópico não tenha mais de 1000 partições, o broker não tenha mais de 2.000 ou 4.000 partições, e o cluster não gerencie mais de 20.000 partições

### Clusters
Alguns conselhos e advertências para clusters em produção. 
- Não é fácil montar um cluster
- Número ímpar de zookeepers, no mínimo três, em datacenters separados
- Rodar os zookeepers e os brokers em servidores separados, isolados de outras aplicações também.
- Implemente monitoração - ELK, NewRelic, Prometheus
- É necessário um bom Kafka Admin
- Tem alternativas de `Kafka as a service` na núvem, evitando alguns custos envolvidos.

### Segmentos
Os tópicos são feitos de partitions, e as partitions são feitas de segmentos, isso é arquivos. Cada arquivo ou segmento contém uma sequencia de dados ordenados por offset, e só um segmento está ativo no momento para escrita 

![segments](images/partitionSegments.png)

Esse sistema permite que seja muito rápida a leitura sequencial, mas seja não tão boa na leitura aleatória.

Podemos configurar o tamanho de cada segmento e o tempo que um segmento fica aberto enquanto não estiver cheio (por padrão, uma semana).

#### Log compaction
O kafka prove uma otimização das chaves para reduzir o tamanho dos segmentos. Uma vez ativada, vai olhar para os segmentos fechados e vai "juntá-los" mantendo só as últimas informações de cada chave:

![logCompactation](images/logCompaction.png)



## Replication cross cluster
When we replicate data in a cluster for some nodes, we call this *replication*. The same action is possible beetwen clusters, and we call this *mirroring*.

*Use cases*:
- DCs in diferent regions.
- Redundancy. If one DC crash, with all data on the other DC, the application can used the second cluster without problems.
- Cloud migrations: Some times we need data from database on one cloud to the other cloud. We can use Kafka connect to replicate this data beetwen DC controlling cross-dc traffic.

Important: Kafka is not recommended to use with some brokers in one datacenter and other in another datacenter in the same cluster!

*Issues of cross-dc communication*:
- High latencies: Latency increases as the distance and the network hops.
- Limited bandwidth
- Higher costs

### Replication Architectures

#### Hub-and-Spokes Architecture
One kafka cluster is the leader and the rest of kafkas are followers. 
- Used when data is produced in multiple datacenters and some consumers need access to the entire data set.
- It allows to applications process only the data local for his datacenter. 
- Data only mirrored once, from local to center (leader). 
- It is simple to deploy, configure and monitor
- Processors in one regional datacenter can’t access data in another
- Use of this pattern is usually limited to only parts of the data set

#### Active-active Architecture
This architecture is used when two or more datacenters share some or all of the data and each datacenter is able to both produce and consume events
- Serve users from a nearby datacenter, without limited availability of data.
- It is redundancy and resilience
- The challenge is avoid conflicts when data is read and update asynchornously in multiple locations.  You will have conflicts and will need to deal with them.
- An other challenge, especially with more than two datacenters, is that you will need a mirroring process for each pair of datacenters and each direction.
- To avoid loops mirroring the same event, you can give for each topic a separate topic in the others datacenters. (Record headers can lead with this problem too)

#### Active-Standby Architecture
If the only requirement for multiple clusters is to support some kind of disaster scenario, this architecture copy all of data to the inactive cluster. In case of disaster on the first DC, we can active the second.
- Simplicity in setup
- No need to worry about access to data, handling conflicts, and other architectural complexities
- Waste a good cluster
- It is currently not possible to perform cluster failover in Kafka without either losing data or having
duplicate events
- The big problem is deal with offsets, because some strategies priorize recovery without loss data but duplicate processes, and some stratgies priorize loss data without duplicity.

#### Strech Clusters
 They are intended to protect the Kafka cluster from failure installing a single Kafka cluster across multiple datacenters. Is not mirroring, only replication.
- The advantages of this architecture are in the synchronous replication
- Both datacenters and all brokers in the cluster are used

Kafka has a cross-cluster mirroring tool call MirrorMaker.

### MirrorMaker
We can replicate all or part of the data. It is the `official` tool for mirroring topics for diferents clusters. Underwood, this tool use a group of consumers and a producer to read from one Kafka cluster and write on the other cluster. To run:

```
$ /opt/kafka_2.12-2.3.0/bin/kafka-mirror-maker.sh --consumer.config ./consumer.properties --producer.config ./producer.properties --new.consumer --num.streams=2 --whitelist "*"
```
The consumer.properties and producer.properties files have a few configurations like consumer group, strategy and bootstrap url. `whitelist` is a regex for choosing topic names.

Some points to consider:
- For an active-active Architecture, careful with ciclye sync, because all events are mirroring between cluster. So, if a topic is mirrored from Cluster A to B, and from Cluster B to A, the events are mirroring eternally.
- If a topic on the destiny cluster doesn't exists, it will be created, with the cluster default configuration. 
- Configure producer to compressed events is recommended, since bandwidth is the main bottleneck for cross-datacenter mirroring
- Separate sensitive topics on a separate mirrorMaker is a good strategy too.
- It strong recomended run on the destiny cluster, consuming data across DC, no producing cross DC. A consumer that is unable to connect to a cluster is much safer than a producer that can’t connect. There is no risk of losing events.

Consume locally and produce remotely when you need to encrypt the data while it is transferred between the datacenters but you don’t need to encrypt the data inside the datacenter. If you use this consume locally and produce remotely, make sure MirrorMaker is configured to never lose events with acks=all and a sufficient number of retries


### uReplicator
Uber develop a tool to mirror data.
- https://eng.uber.com/ureplicator-apache-kafka-replicator/
- https://github.com/uber/uReplicator

Uber implement a wrapper arround MirrorMaker with Apache Helix to manage the partition list. The motivation was to control the rebalancing delays and the dificulty to add new topics that not matched with running pattern.

### Confluent's Replicator
This tool resolves two problems. 
- Configuration: If topic changes configuration on DC1 (retention, partitons), we don't realise and on disaster DC may be a problem when we need to use and configurations are diferent.
- Active-active challanges: When we have more than two DCs, we need more and more MirrorMaker's cluster to mirror events between all datacenters.

The Confluent's Replicator mirrors topic configurations and a single process can sync data with a group of clusters.

