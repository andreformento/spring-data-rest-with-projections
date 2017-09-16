# Spring data rest with projections

## References

* https://docs.spring.io/spring-data/rest/docs/current/reference/html/#projections-excerpts
* https://github.com/spring-projects/spring-data-examples/tree/master/rest/security


## Build and run
```
gradle bootRun
```

## APIs

## Magic 
You can do a post with `ollie` user and after with `greg`. 
When you do a request `GET /houses` without filter with each user, the response will contain just data from user. 
See [HouseRepository.findAll()](src/main/java/com/formento/projections/house/HouseRepository.java#L14) that have a filter `"{ user: ?#{principal.username} }"` that take just info about the user. 

*How it works?* You need configure the evaluation like the class [SecurityEvaluationConfig](src/main/java/com/formento/projections/config/SecurityEvaluationConfig.java) and add the dependency `compile('org.springframework.security:spring-security-data')`.

### Example with a house API

1. Create a house to `ollie`
```
curl -i -X POST -d '{"address": "Some localization on the earth"}' -H "Content-Type: application/json" localhost:8080/houses -u ollie:gierke
```

2. Create a house to `greg`
```
curl -i -X POST -d '{"address": "Some localization on the mars"}' -H "Content-Type: application/json" localhost:8080/houses -u greg:turnquist
```

3. List houses from `ollie`. The result contain just your own houses
```
curl localhost:8080/houses -u ollie:gierke
```

4. Now list houses from `greg`. The result contain just your own houses
```
curl localhost:8080/houses -u greg:turnquist
```

5. You can delete some house from `greg`
```
curl -i -X DELETE -H "Content-Type: application/json" localhost:8080/houses/<some-uuid-valid-from-greg> -u greg:turnquist
```
The response will be http status code `204 No Content` if the uuid is from greg. Else, the status code will be `403 Forbidden` if is from another user. And, is of course, `404 Not Found` when do not exists 

## Just a simple another API example to explain about security

#### Get employees
```
curl localhost:8080/employees
```
No security required!

#### About security
```
curl -X POST -d '{"firstName": "Saruman", "lastName": "the evil one", "title": "the White"}' localhost:8080/employees
```
This is not good - you need to be authenticated :sweat:

#### With security but USER level credentials :sweat_smile:
```
curl -X POST -d '{"firstName": "Saruman", "lastName": "the evil one", "title": "the White"}' localhost:8080/employees -u greg:turnquist
```
You are now denied due to not having sufficient authorization.

#### Now with security but ADMIN level credentials :smile:
```
curl -i -X POST -d '{"firstName": "Saruman", "lastName": "the evil one", "title": "the White"}' -H "Content-Type: application/json" localhost:8080/employees -u ollie:gierke
```

#### Get items
```
curl localhost:8080/items -u greg:turnquist
```
