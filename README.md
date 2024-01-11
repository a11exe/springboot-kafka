# Spring Boot Kafka example
Example using kafka with spring-boot

* [UI Swagger API app1](http://localhost:8082/swagger-ui/index.html)
* [UI Swagger API app2](http://localhost:8083/swagger-ui/index.html)

## Example demonstrate concurrent processing using kafka.

### Api provides functionality:

* Create operations
* View operations
* Run operations
* Run operations through kafka
* Delete operations

### To demonstrate concurrent processing:
#### run services:
```
sh start.sh
```
script starts db and kafka in docker. It creates topic `operation` with 2 partitions
#### start first application
```
docker compose up app1
```

after application started, consumer would be attached to topic partition.
`KafkaMessageListenerContainer    : 1: partitions assigned: [operation-0, operation-1]`

#### start second application
```
  docker compose up app2
```

after application started, partitions would be reassigned.

first app
`KafkaMessageListenerContainer    : 1: partitions assigned: [operation-0]`

second app
`KafkaMessageListenerContainer    : 1: partitions assigned: [operation-1]`

#### create operation:
using api ui or curl
```
curl -X 'POST' \
  'http://localhost:8082/api/v1/operations/generate' \
  -H 'accept: */*' \
  -d ''
```
#### run operations:
run operations without kafka by app1 (using api ui or curl)
```
curl -X 'POST' \
  'http://localhost:8082/api/v1/operations/run' \
  -H 'accept: */*' \
  -d ''
```
### check execution result:
check operations performed by app1
```
curl -X 'GET' \
  'http://localhost:8082/api/v1/operations?comment=SERVER-1' \
  -H 'accept: */*'
```
checking operations performed by the app1 will return a list.
```
curl -X 'GET' \
  'http://localhost:8082/api/v1/operations?comment=SERVER-2' \
  -H 'accept: */*'
```
result will be empty. Because we ran execution not concurrently.
### delete operations:
```
curl -X 'DELETE' \
  'http://localhost:8082/api/v1/operations' \
  -H 'accept: */*'
```
#### run operations through kafka:
run operations with kafka by app1 (using api ui or curl)
```
curl -X 'POST' \
  'http://localhost:8082/api/v1/operations/run-through-kafka' \
  -H 'accept: */*' \
  -d ''
```
in log files we will notice the processing in first and second app.

#### check execution result:
check operations performed by app1
```
curl -X 'GET' \
  'http://localhost:8082/api/v1/operations?comment=SERVER-1' \
  -H 'accept: */*'
```
we have some operations processed by app1

then check operations performed by app2
```
curl -X 'GET' \
  'http://localhost:8082/api/v1/operations?comment=SERVER-2' \
  -H 'accept: */*'
```
and we also have some operations processed by app2 too