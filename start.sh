./gradlew clean build
docker compose down
#docker rmi -f confluentinc/cp-zookeeper:latest
#docker rmi -f confluentinc/cp-kafka:latest
docker rmi -f springboot-kafka-app1
docker rmi -f springboot-kafka-app2
docker compose up db zookeeper kafka init-kafka