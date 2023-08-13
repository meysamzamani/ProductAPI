FROM amazoncorretto:17-alpine

WORKDIR /app

COPY gradlew .
COPY gradle gradle

COPY . .

RUN chmod +x gradlew

RUN ./gradlew build

EXPOSE 8080

CMD ["java", "-jar", "build/libs/ProductAPI-0.0.1-SNAPSHOT.jar"]
