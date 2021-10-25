# Auction API

### Reference Documentation

The following guides illustrate how to use some features in details.
All below methods are described also under Swagger endpoint:
```localhost:8080/auction-api/swagger-ui.html```

* create new user: (endpoint secured with Basic Auth, credentials are stored within application properties - 
'''admin:pass'')

  ```POST    localhost:8080/auction-api/users```

* get all users (helper endpoint for getting userToken):

  ```GET      localhost:8080/auction-api/users```


For building project:

```mvn clean install```

For api documentation and testin visit:
* [localhost:8080/auction-api/swagger-ui.html](localhost:8080/auction-api/swagger-ui.html)
