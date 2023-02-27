FROM openjdk:11
RUN mkdir /app && cd /app && mkdir bob
COPY target/bobcody.jar /app/bob
WORKDIR /app/bob

ENTRYPOINT ["java", "-Dspring.config.location=/app/bob/application.yml", "-jar", "/app/bob/bobcody.jar"]

#контенрейн только с Java11, БД будет использоваться та, что на хосте. Проперти тоже лежат на хосте
#для старта контейнера:
#docker run -it -d --name=bob_cody_bot --network="host" -v [/path/to/application.yml/on/host]:/app/bob/application.yml $image_name bash