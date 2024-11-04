# Startcode for java deep dive II week

## Task 1
done
## Task 2
created all the entities,dtos and daos

## Task 3

    HTTP method             REST Resource	                    Status (ok)     Not okay
    GET                     /trips                              200             404
    GET                     /trips/{id}                         200             404
    POST                    /trips                              201             400
    PUT                     /trips/{id}                         200             404
    DELETE                  /trips/{id}                         204             404
    PUT                     /trips/{tripId}/guides/{guideId}    200             404
    POST                    /trips/populate                     201             400

### Task 4
I made one class called ErrorResponse that just send a message out to the user when getting an error.

### Task 5
```json
[
  {
    "guideId": 1,
    "totalPrice": 1200.0
  },
  {
    "guideId": 2,
    "totalPrice": 950.0
  },
  {
    "guideId": 3,
    "totalPrice": 1500.0
  },
  {
    "guideId": 4,
    "totalPrice": 1800.0
  },
  {
    "guideId": 5,
    "totalPrice": 800.0
  },
  {
    "guideId": 6,
    "totalPrice": 1200.0
  },
  {
    "guideId": 7,
    "totalPrice": 950.0
  },
  {
    "guideId": 8,
    "totalPrice": 1500.0
  },
  {
    "guideId": 9,
    "totalPrice": 1800.0
  },
  {
    "guideId": 10,
    "totalPrice": 800.0
  }
]
```
```json
[
  {
    "id": 1,
    "startTime": [
      2024,
      12,
      1,
      9,
      0
    ],
    "endTime": [
      2024,
      12,
      1,
      17,
      0
    ],
    "startposition": "Mount Everest Base",
    "name": "Mountain Expedition",
    "price": 1200.0,
    "category": "snow",
    "guide": {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "johndoe@example.com",
      "phone": "555-0101",
      "yearsOfExperience": 5
    }
  },
  {
    "id": 6,
    "startTime": [
      2024,
      12,
      1,
      9,
      0
    ],
    "endTime": [
      2024,
      12,
      1,
      17,
      0
    ],
    "startposition": "Mount Everest Base",
    "name": "Mountain Expedition",
    "price": 1200.0,
    "category": "snow",
    "guide": {
      "id": 6,
      "firstName": "John",
      "lastName": "Doe",
      "email": "johndoe@example.com",
      "phone": "555-0101",
      "yearsOfExperience": 5
    }
  }
]
```

### Task 6
Didn't have time to make task 6

### Task 7

started but dind't get to make test for all the endpoints
### Task 8
Added roles to the routes 

#8.3 
I would either make a admin user or just change the permmision so that it works-
