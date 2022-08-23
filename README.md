# USER API

#### CREATE IMAGE

> sbt docker:publishLocal

#### RUN APP WITH

> cd docker && docker-compose up -d

## DOCS

### http://localhost:8080/docs/

## Example curl requests

#### [GET USER]

> curl localhost:8080/api/user/1

&ensp;

#### [GET ALL USERS]

> curl -X "GET" "localhost:8080/api/users"

&ensp;

#### [SELECT USERS (for more see docs)]

> curl -X "POST" "localhost:8080/api/user/select?gender=male" -H "Content-Type: application/json" -d '{"page": 1, "pageSize": 50, "sortBy": [{"columnName": "id", "direction":"ASC"}]}'

&ensp;

#### [SAVE USER]

> curl -X "POST" "localhost:8080/api/user" -H "Content-Type: application/json" -d '{"firstName": "Tom", "lastName": "Hanks", "gender": "male", "age": 55}'

&ensp;

#### [DELETE USER]

> curl -X "DELETE" "localhost:8080/api/user/1"

#### [UPDATE USER]

> curl -X "POST" "localhost:8080/api/user/1" -H "Accept: application/json" -H "Content-Type: application/json" -d '{"firstName": "Tom", "lastName": "Hanks", "gender": "male", "age": 55}'