# Spring data rest with projections

### References

* https://docs.spring.io/spring-data/rest/docs/current/reference/html/#projections-excerpts
* https://github.com/spring-projects/spring-data-examples/tree/master/rest/security


## Simple API

### Build and run
```
gradle bootRun
```

### API


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

## House API

### Register
```
curl -i -X POST -d '{"address": "Some localization on the earth"}' -H "Content-Type: application/json" localhost:8080/houses -u ollie:gierke
```

### List
```
curl localhost:8080/houses -u greg:turnquist
```
