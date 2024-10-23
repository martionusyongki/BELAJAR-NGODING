# Address API Spec

## Create Address

Endpoint : POST /api/contacts/{idContact/addresses

Request Header :

- X-API-TOKEN : token (Mandatory)

Request Body :

```json
{
  "street" : "jalan",
  "city" : "kota",
  "province" : "provinsi",
  "country" : "negara",
  "postalCode" : "123123"
}
```
Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "street" : "jalan",
    "city" : "kota",
    "province" : "provinsi",
    "country" : "negara",
    "postalCode" : "123123"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Contact Not Found"
}
```

## Update Address

Endpoint : PUT /api/contacts/{idContact/addresses/{idAddress}

Request Header :

- X-API-TOKEN : token (Mandatory)

Request Body :

```json
{
  "street" : "jalan",
  "city" : "kota",
  "province" : "provinsi",
  "country" : "negara",
  "postalCode" : "123123"
}
```
Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "street" : "jalan",
    "city" : "kota",
    "province" : "provinsi",
    "country" : "negara",
    "postalCode" : "123123"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Address Not Found"
}
```

## GET Address

Endpoint : GET /api/contacts/{idContact/addresses/{idAddress}

Request Header :

- X-API-TOKEN : token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "street" : "jalan",
    "city" : "kota",
    "province" : "provinsi",
    "country" : "negara",
    "postalCode" : "123123"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Address Not Found"
}
```

## Remove Address

Endpoint : DELETE /api/contacts/{idContact/addresses/{idAddress}

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
  "errors" : "Address Not Found"
}
```

## List Address

Endpoint : GET /api/contacts/{idContact}/addresses

Request Header :

- X-API-TOKEN : token (Mandatory)

Response Body (Success) :

```json
{
  "data" : [
    {
      "id" : "random-string",
      "street" : "jalan",
      "city" : "kota",
      "province" : "provinsi",
      "country" : "negara",
      "postalCode" : "123123"
    }
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "Contact Not Found"
}
```