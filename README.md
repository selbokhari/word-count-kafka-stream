# Implémentation d'un compteur de mots avec Kafka Streams

Ce dépôt contient un projet Kafka Streams qui calcule l'occurrence de chaque mot envoyé au topic `input-topic` et publie le résultat dans le topic `output-topic`.

## Prérequis

- Docker et Docker Compose
- Java (version 21 ou 17)
- IDE (IntelliJ)

## Installation et exécution

Suivez ces étapes pour exécuter le projet :

### 1. Démarrer les services Kafka

Lancez Zookeeper, Kafka et Kafka-setup :

```bash
cd ./docker
```
```bash
docker-compose -f kafka-core.yml up -d
```

### 2. Accéder au conteneur Kafka

Ouvrez le conteneur Kafka dans un terminal :

```bash
docker exec -it kafka bash
```

### 3. Écouter les messages du topic `output-topic`

Dans le terminal du conteneur Kafka, exécutez :

```bash
kafka-console-consumer.sh \ 
    --bootstrap-server localhost:9092 \ 
    --topic output-topic \ 
    --from-beginning \ 
    --formatter kafka.tools.DefaultMessageFormatter \ 
    --property print.key=true \ 
    --property print.value=true \ 
    --key-deserializer org.apache.kafka.common.serialization.StringDeserializer \ 
    --value-deserializer org.apache.kafka.common.serialization.LongDeserializer
```

### 4. Démarrer le Kafka Stream

Exécutez l'application des kafka-streams.

```bash
mvn spring-boot:run
```

### 5. Publier des messages sur le topic `input-topic`

Ouvrez le conteneur Kafka dans un terminal :

```bash
docker exec -it kafka bash
```

Utilisez le producteur Kafka pour envoyer des messages au topic `input-topic`.

```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic input-topic
```