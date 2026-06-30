# Kafka Log Compaction Demo

A Spring Boot project demonstrating **Kafka Log Compaction** using Producer and Consumer applications.

This project explains how Kafka stores multiple records with the same key and how the Log Cleaner eventually retains only the latest value for each key.

---

# Technologies

- Java 21
- Spring Boot 3.5.6
- Spring Kafka
- Apache Kafka
- AKHQ
- Docker
- Maven

---

# Project Structure

```
kafka-log-compaction-demo
│
├── producer-service
│
├── consumer-service
│
├── docker-compose.yml
│
└── README.md
```

---

# Architecture

```
                Spring Boot Producer
                        |
                        |
                customer-topic
              (cleanup.policy=compact)
                        |
                        |
               Kafka Log Cleaner
                        |
                        |
               Spring Boot Consumer
                        |
                        |
                      AKHQ
```

---

# How It Works

Customer information is published to Kafka using **customerId** as the message key.

Example:

```
Key = 101

Value = Delhi
```

Customer updates city.

```
Key = 101

Value = Mumbai
```

Customer updates again.

```
Key = 101

Value = Gurgaon
```

Initially Kafka stores all records.

| Offset | Key | City |
|-------:|-----|--------|
|0|101|Delhi|
|1|101|Mumbai|
|2|101|Gurgaon|

Kafka **does not overwrite** previous records.

Instead, it appends every message.

Later, the **Log Cleaner** runs in the background and compacts the topic.

Final state becomes

| Key | City |
|------|------|
|101|Gurgaon|

Only the latest value for the key is retained.

---

# Topic Configuration

```java
@Bean
public NewTopic customerTopic() {

    return TopicBuilder
            .name("customer-topic")
            .partitions(3)
            .replicas(1)
            .config(
                TopicConfig.CLEANUP_POLICY_CONFIG,
                TopicConfig.CLEANUP_POLICY_COMPACT
            )
            .build();
}
```

---

# Producer API

### Publish Customer

```
POST /customers
```

Request

```json
{
    "customerId":"101",
    "name":"Neelu",
    "city":"Delhi"
}
```

Update

```json
{
    "customerId":"101",
    "name":"Neelu",
    "city":"Mumbai"
}
```

Update Again

```json
{
    "customerId":"101",
    "name":"Neelu",
    "city":"Gurgaon"
}
```

---

# Consumer

The consumer listens to the compacted topic.

```java
@KafkaListener(
    topics = "customer-topic",
    groupId = "customer-group"
)
```

---

# Testing

## 1. Start Kafka

```bash
docker compose up -d
```

Verify

```bash
docker ps
```

---

## 2. Start Producer

Run

```
ProducerApplication
```

---

## 3. Start Consumer

Run

```
ConsumerApplication
```

---

## 4. Open AKHQ

```
http://localhost:30081
```

---

## 5. Send Requests

```
POST /customers
```

Send the same **customerId** multiple times with different city values.

Observe the topic in AKHQ.

Initially all records are visible.

After Log Compaction runs, Kafka keeps only the latest value for the same key.

---

# Log Compaction vs Retention

| Log Retention | Log Compaction |
|---------------|----------------|
| Deletes records based on time or size | Keeps the latest record for each key |
| Default cleanup policy | Requires `cleanup.policy=compact` |
| Used for event history | Used for latest state |

---

# Key Points

- Kafka stores messages as an append-only log.
- Log Compaction works only for records with a message key.
- Messages are **not compacted immediately**.
- Kafka performs compaction asynchronously using the Log Cleaner.
- Ordering is preserved within a partition.
- Offsets are immutable even after compaction.

---

# Interview Questions Covered

- What is Log Compaction?
- Difference between Log Compaction and Retention?
- Why is the message key important?
- Does Kafka overwrite old messages?
- When does Log Compaction run?
- Does Kafka immediately delete duplicate records?
- Can Log Compaction affect offsets?
- Why is Log Compaction useful for stateful applications?

---

# Future Enhancements

- Tombstone Records
- Retry Topics
- Dead Letter Topic (DLT)
- Kafka Transactions
- Exactly Once Semantics (EOS)
- Schema Registry
- Avro Serialization

---

# Author

**Neelu Sahai**

Spring Boot | Java | Kafka | Microservices
