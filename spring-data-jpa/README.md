# Study on Spring-data-jpa

## Prerequisite
- add dependenchy
```$xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.2.2</version>
</dependency>
```

- Install and Run PostgreSQL (docker)
```$bash
docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=cbbatte -e POSTGRES_DB=springdata --name postgres_boot -d postgres
docker exec -i -t postgres_boot bash
su - postgres
psql --username cbbatte --dbname springdata
```


 