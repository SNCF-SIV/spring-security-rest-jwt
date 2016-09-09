# Spring MVC application with stateless security using JWT [![Build Status](https://travis-ci.org/SNCF-SIV/spring-security-rest-jwt.svg?branch=master)](https://travis-ci.org/SNCF-SIV/spring-security-rest-jwt)

Inspired by [LynAs' Spring Security JWT REST Stateless](https://github.com/lynas/spring-security-jwt-rest-stateless) and adapted :

 * Replacing Gradle to Maven
 * Replacing MySQL to MongoDB
 * Some minor changes (package names)
 * Spring Security configuration has been corrected
 * library versions updated (to conform to those of Spring Boot 1.4)
 * Correcting the filter chain
 * The token is not revalidated against database each time
 * No *refresh token* functionality here
 * Moving to [Nimbus JOSE JWT](http://connect2id.com/products/nimbus-jose-jwt) implementation.
 * Correcting README


#### Requirements

 * Java 8
 * MongoDB


#### Configuring MongoDB

 * Create a new collection called `app_users`.
 * Add a new user inside MongoDB using this script :


```javascript
db.getCollection('app_users').insert( {
    "username" : "florent",
    "password" : "$2a$10$aS/lF2c/9JWPUjDHfJ/zTed1ihGBgfX/7xnGTOM5/lW59X4FHalSi",
    "authorities" : "ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_MANAGER"
    });

db.getCollection('app_users').insert( {
    "username" : "nicolas",
    "password" : "$2a$10$aS/lF2c/9JWPUjDHfJ/zTed1ihGBgfX/7xnGTOM5/lW59X4FHalSi",
    "authorities" : "ROLE_EMPLOYEE, ROLE_MANAGER"
    });

```

 * Adapt the configuration in `src/main/resources/application.properties`.

#### Building and installing the application

 * Run `mvn clean install`.
 * deploy the built WAR on your favorite servlet container (JBoss, Tomcat, ...)

#### Testing authentication

 * By using [DHC](https://dhc.restlet.com/), make a `POST` request on `http://localhost:8080/spring-security-rest-jwt/auth` with the body (also make sure your `Content-Type` header is set to `application/json`.):

```js
{
  "username" : "florent",
  "password" : "123456"
}
```

you will get result

```js
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJseW5hcyIsImF1Z..."
}
```

You can then `GET` with header `Authorization` the resource `/protected`.

```
GET http://localhost:8080/spring-security-jwt-rest-stateless/protected
Authorization : "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJseW5hcyIsImF1Z..."
```

## Testing Authorization

Make a `POST` request on `http://localhost:8080/spring-security-rest-jwt/auth` with the body (also make sure your `Content-Type` header is set to `application/json`.):
```js
{
  "username" : "nicolas",
  "password" : "123456"
}
```

you will get result:

```js
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJseW5hcyIsImF1Z..."
}
```

You can then `GET` with header `Authorization` the resource `/protected`.

```
http://localhost:8080/spring-security-jwt-rest-stateless/protected
X-Auth-Token : "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJseW5hcyIsImF1Z..."
```

Returns a `403` (`Forbidden`) meaning your access is denied.

## Further documentation
In this approach, the token is verified on each request but only to check its signature and expiration.
No call to database is made during this validation.
The user informations are carried by the token.
Which means that if you change informations like roles, name, they will be refreshed only when a new token is regenerated.
Because of this (and also because no revocation mecanism is supported), we recommand a short lifetime for the JWT token.

This implementation uses a symmetric key for signing the token.
This is done because here, no JWT signature check is done by the frontend.
We provided this example in a "REST Backend to RIA Frontend". In this case, the client and the server communicates through HTTPS, and the authenticity of the server is provided by SSL/TLS protocols over HTTP.
The client then doesn't need to check the signature of the JWT token again.

We highly recommand the use of HTTPS when using JWT !!


## Why NIMBUS JOSE JWT(instead of jjwt) ?
JJWT is simpler to implements but doesn't provide JWE.
For extensibility, I prefered the use of Nimbus JOSE JWT, in case I want to add encrypted content inside the JWT claims.
Furthermore, if I need to use assymetric key, it's possible to expose public keys with JWK though utility classes provided by Nimbus Jose (JJWT doesn't implement JWK).
