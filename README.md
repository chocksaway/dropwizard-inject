# dropwizard-inject

Redis running

```
$ docker run --name my-redis -p 6379:6379 -d redis
$ docker ps
CONTAINER ID   IMAGE          COMMAND                  CREATED         STATUS         PORTS                                       NAMES
15319bc98457   redis          "docker-entrypoint.s…"   4 seconds ago   Up 3 seconds   6379/tcp                                    myredis
```

Exec into redis (docker container)
```
$ docker exec -it 15319bc98457 redis-cli
27.0.0.1:6379> keys "*"
(empty array)
127.0.0.1:6379>
```

POST
```
curl --location --request POST 'http://127.0.0.1:9000/person' \
--header 'Content-Type: application/json' \
--data-raw '{
"firstName": "john",
"lastName": "smith",
"email": "email",
"address": "address"
}'
```

Confirm that data is being stored in redis

```
$ docker exec -it 15319bc98457 redis-cli
127.0.0.1:6379> keys "*"
1) "john"
127.0.0.1:6379> 
```

PUT
```
$ curl --location --request PUT 'http://127.0.0.1:9000/person/john' --header 'Content-Type: application/json' --data-raw '{
"firstName": "john",
"lastName": "smith-put",
"email": "email",
"address": "address"
}'
```

GET
```
curl --location --request GET 'http://127.0.0.1:9000/person/john'
```

DELETE
```
$ curl --location --request DELETE 'http://127.0.0.1:9000/person/john'
{"firstName":"john","lastName":"smith-put","email":"email","address":"address"}
$
```






