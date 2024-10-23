# Contact API Spec

## Create Contact

Endpoint : POST /api/contacts

Request Header :

- X-API-TOKEN : token (Mandatory)

Request Body :

```json
{
  "firstname" : "Martionus",
  "lastname" : "Yongki",
  "email" : "email@example.com",
  "phone" : "0812345678"
}
```
Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "firstname" : "Martionus",
    "lastname" : "Yongki",
    "email" : "email@example.com",
    "phone" : "0812345678"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Failed"
}
```

## Update Contact

Endpoint : PUT /api/contacts/{idContact}

Request Header :

- X-API-TOKEN : token (Mandatory)

Request Body :

```json
{
  "firstname" : "Martionus",
  "lastname" : "Yongki",
  "email" : "email@example.com",
  "phone" : "0812345678"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "firstname" : "Martionus",
    "lastname" : "Yongki",
    "email" : "email@example.com",
    "phone" : "0812345678"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Failed"
}
```

## Get Contact

Endpoint : GET /api/contacts/{idContact}

Request Header :

- X-API-TOKEN : token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "firstname" : "Martionus",
    "lastname" : "Yongki",
    "email" : "email@example.com",
    "phone" : "0812345678"
  }
}
```

Response Body (Failed, 404) :

```json
{
  "errors" : "Contact Not Found"
}
```

## Search Contact

Endpoint : GET /api/contacts

Query Param :

- name : String, contact firstname or lastname, using like query, optional
- phone : String, contact phone, using like query, optional
- email : String, contact email, using like query, optional
- page : Integer, start from 0
- size : Integer, default 10

Request Header :

- X-API-TOKEN : token (Mandatory)

Response Body (Success) :

```json
{
  "data" : [
    {
      "id" : "random-string",
      "firstname" : "Martionus",
      "lastname" : "Yongki",
      "email" : "email@example.com",
      "phone" : "0812345678"
    }
  ],
  "paging" : {
    "currentPage" : 0,
    "totalPage" : 10,
    "size" : 10
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## Remove Contact

Endpoint : DELETE /api/contacts/{idContact}

Request Header :

- X-API-TOKEN : token (Mandatory)

Response Body (Success) :

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Contact Not Found"
}
```