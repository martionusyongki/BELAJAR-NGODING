# USER API SPEC

## Register User

Endpoint : POST /api/users

Request Body :

```json
{
  "username" : "",
  "password" : "",
  "name" : ""
}
```

Response Body (Success) :

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Username must not blank"
}
```

## Login User

Endpoint : POST /api/auth/login

Request Body :

```json
{
  "username" : "",
  "password" : ""
}
```

Response Body (Success) :

```json
{
  "data" : {
    "token" : "TOKEN",
    "expiredAt" : 123456 // milliseconds
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Username or Password wrong"
}
```

## Get User

Endpoint : GET /api/users/current

Request Header :

- X-API-TOKEN : token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "username" : "",
    "name" : ""
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Unautorized"
}
```

## Update User

Endpoint : PATCH /api/users/current

Request Header : 

- X-API-TOKEN : token (Mandatory)

Request Body :

```json
{
  "name" : "", //put if only want to update name
  "password" : "" //put if only want to update password
}
```

Response Body (Success) :

```json
{
  "data" : {
    "username" : "",
    "name" : ""
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Unautorized"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : token (Mandatory)

Response Body (Success) :

```json
{
  "data" : "OK"
}