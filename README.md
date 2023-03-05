# BudgetManager
Manage your daily income and expenses with BudgetManager. Categorize, track, and stay on top of your finances with ease.

# Rest APIs Documentation

* [Admin Controller](#admin-section) : Endpoints for managing budgets and users
* [Authorization Controller ](#authorization-section) : Endpoints for user authentication and registration
* [Budgets Controller](#budget-section) : Endpoints for managing user budgets
* [Chat Controller](#chat-section) : Endpoints for sending and receiving chat messages
* [Ticket Controller](#ticket-section) : Endpoints for creating and deleting tickets
* [User Controller](#user-section) : Operations related to user management
---
## AuthorizationController
---

### <a name="authorization-section"></a>


### HTTP REQUEST

```
POST /auth/api/login
```

#### Description

Authenticates a user by username and password

#### Parameters

|Type| Name                             |Schema|
|---|----------------------------------|---|
|**Header**| **Content-Type**  <br>*required* |enum (application/json)|
|**Body**| **body**  <br>*required*         |[UserLoginDto](#userlogindto)|

#### Consumes

* `application/json`

#### Produces

* `text/plain`

##### Request header

```
'Cookie': 'JSESSIONID=${session.cookies}'
```

##### Request body

```json
{
  "login": "string",
  "password": "string"
}
```

##### Response

```
Username signed successfully
```

---

### HTTP REQUEST

```
POST /auth/api/logout
```

#### Description

Logs out a user

#### Produces

* `text/plain`

##### Response

```
User logged out.
```

---

### HTTP REQUEST

```
POST /auth/api/register
```

#### Description

Registers a new user

#### Parameters

| Type       | Name                             | Schema                              |
|------------|----------------------------------|-------------------------------------|
| **Header** | **Content-Type**  <br>*optional* | enum (application/json)             |
| **Body**   | **body**  <br>*required*         | [UserRegisterDto](#userregisterdto) |

#### Consumes

* `application/json`

#### Produces

* `text/plain`

##### Request header

```
'Cookie': 'JSESSIONID=${session.cookies}'
```

##### Request body

```json
{
  "login": "string",
  "password": "string"
}
```

##### Response

```
User registered successfully
```

---

## USER CONTROLLER

---

### <a name="user-section"></a>

### HTTP REQUEST

```
GET /api/user
```

#### Description

Retrieves the currently logged in user.

##### Response

```json
{
  "id": 0,
  "role": {},
  "login": "string",
  "budget": [
    "budgets"
  ],
  "tickets": [
    "tickets"
  ],
  "messages": [
    "messages"
  ],
  "password": "string"
}
```

---

### HTTP REQUEST

```
POST /api/user/changeLogin
```

#### Description

Changes user's login to a new one.

#### Parameters

|Type|Name|Schema|
|---|---|---|
|**Header**|**Content-Type**  <br>*optional*|enum (application/json)|
|**Body**|**body**  <br>*required*|[UserLoginChangeDto](#userloginchangedto)|

#### Consumes

* `application/json`

#### Produces

* `text/plain`

##### Request header

```
'Cookie': 'JSESSIONID=${session.cookies}'
```

##### Request body

```json
{
  "newLogin": "string",
  "userPassword": "string"
}
```

##### Response

```
Login successfully change to {login}
```

<a name="changepassword"></a>

---

### HTTP REQUEST

```
POST /api/user/changePassword
```

#### Description

Changes user's password to a new one.

#### Parameters

|Type| Name                             |Schema|
|---|----------------------------------|---|
|**Header**| **Content-Type**  <br>*required* |enum (application/json)|
|**Body**| **body**  <br>*required*         |[UserPasswordChangeDto](#userpasswordchangedto)|

#### Consumes

* `application/json`

#### Produces

* `text/plain`

##### Request header

```
'Cookie': 'JSESSIONID=${session.cookies}'
```

##### Request body

```json
{
  "newPassword": "string",
  "oldPassword": "string"
}
```

##### Response

```
Password successfully changed to {pass}
```


---

## BudgetController

---

### <a name="budget-section"></a>

### HTTP REQUEST

```
POST /api/budget
```

#### Description

Adds a new budget for the logged in user

#### Parameters

|Type| Name                             |Description|Schema|
|---|----------------------------------|---|---|
|**Header**| **Content-Type**  <br>*optional* ||enum (application/json)|
|**Body**| **budgetDto**  <br>*required*      ||[BudgetDto](#budgetdto)|

#### Consumes

* `application/json`

#### Produces

* `text/plain`

##### Request body

```json
{
  "value": 0,
  "budgetType": "INCOME",
  "incomeCategory": {},
  "expenseCategory": {}
}
```

##### Response

```
Budget item added
```

---

### HTTP REQUEST

```
GET /api/budget/count/total
```

#### Description

Returns the total value of all budgets for the logged in user

#### Produces

* `text/plain`

##### Response

```
80000
```

---

### HTTP REQUEST

```
DELETE /api/budget/deleteAll
```

#### Description

Deletes all budgets for the logged in user

#### Produces

* `text/plain`

##### Response

```
All budgets deleted
```

### HTTP REQUEST

```
GET /api/budget/expense/findAll/{expenseCategory}
```

#### Description

Returns a list of all budgets for the logged in user with the specified expense category

#### Parameters

| Type      | Name             | Description                                                                                               | Schema |
|-----------|------------------|-----------------------------------------------------------------------------------------------------------|--------|
| required* | Expense category | enum (FOOD, TRANSPORT, HOUSING, HEALTHCARE, ENTERTAINMENT, EDUCATION, SHOPPING, INSURANCE, TAXES, OTHERS) |        |

#### Produces

* `application/json`

##### Response

```json
 [
  {
    "id": 627,
    "budgetType": "EXPENSE",
    "incomeCategory": null,
    "expenseCategory": "OTHERS",
    "value": -900,
    "historyDayNumber": "2023-02-23"
  },
  {
    "id": 635,
    "budgetType": "INCOME",
    "incomeCategory": null,
    "expenseCategory": "OTHERS",
    "value": 500,
    "historyDayNumber": "2023-02-23"
  }
]
```

---

### HTTP REQUEST

```
GET /api/budget/findAll
```

#### Description

Returns a list of all budgets for the logged in user

#### Produces

* `application/json`

##### Response

```json
[
  {
    "id": 617,
    "budgetType": "INCOME",
    "incomeCategory": "BONUS",
    "expenseCategory": null,
    "value": 900,
    "historyDayNumber": "2023-02-21"
  },
  {
    "id": 618,
    "budgetType": "EXPENSE",
    "incomeCategory": null,
    "expenseCategory": "HOUSING",
    "value": 500,
    "historyDayNumber": "2023-02-21"
  }
]
```

---

### HTTP REQUEST

```
GET /api/budget/findAll/{date}
```

#### Description

Returns a list of all budgets for the logged in user with the specified date

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**date**  <br>*required*|Historical date|string|

#### Produces

* `application/json`

##### Response

```json
   [
  {
    "id": 623,
    "budgetType": "EXPENSE",
    "incomeCategory": null,
    "expenseCategory": "OTHERS",
    "value": -6700,
    "historyDayNumber": "2023-02-23"
  },
  {
    "id": 624,
    "budgetType": "INCOME",
    "incomeCategory": "INVESTMENTS",
    "expenseCategory": null,
    "value": 9000,
    "historyDayNumber": "2023-02-23"
  }
]
```

---

### HTTP REQUEST

```
GET /api/budget/income/findAll/{incomeCategory}
```

#### Description

Returns a list of all budgets for the logged in user with the specified income category

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**incomeCategory**  <br>*required*|Income category|enum (SALARY, BONUS, INVESTMENTS, OTHERS)|

#### Produces

* `application/json`

##### Response

```json
[
  {
    "id": 607,
    "budgetType": "INCOME",
    "incomeCategory": "SALARY",
    "expenseCategory": null,
    "value": 5000,
    "historyDayNumber": "2023-02-19"
  },
  {
    "id": 608,
    "budgetType": "INCOME",
    "incomeCategory": "SALARY",
    "expenseCategory": null,
    "value": 45000,
    "historyDayNumber": "2023-02-19"
  }
]
```

### HTTP REQUEST

```
PUT /api/budget/{id}
```

#### Description

Modifies the selected budget

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Content-Type**  <br>*optional*||enum (application/json)|
|**Body**|**body**  <br>*required*||[BudgetDto](#budgetdto)|

#### Consumes

* `application/json`

#### Produces

* `text/plain`

##### Request header

```
'Cookie': 'JSESSIONID=${session.cookies}'
```

##### Request body

```json
{
  "value": 0,
  "budgetType": {},
  "incomeCategory": {},
  "expenseCategory": {}
}
```

##### Response

```
Budget changed
```

---

### HTTP REQUEST

```
DELETE /api/budget/{id}
```

#### Description

Deletes the selected budget

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|Budget ID|integer (int64)|

#### Produces

* `text/plain`

##### Response

```
Budget deleted
```




---

## TicketController

---

### <a name="ticket-section"></a>

### HTTP REQUEST

```
POST /api/ticket
```

#### Description

Creates a new ticket with the specified data

#### Parameters

|Type| Name                             |Description|Schema|
|---|----------------------------------|---|---|
|**Header**| **Content-Type**  <br>*required* ||enum (application/json)|
|**Body**| **body**  <br>*required*         ||[TicketDto](#ticketdto)|

#### Consumes

* `application/json`

#### Produces

* `text/plain`

##### Request header

```
'Cookie': 'JSESSIONID=${session.cookies}'
```

##### Request body

```json
{
  "message": "message",
  "subject": "subject"
}
```

##### Response

```
Ticket has been created
```

---

### HTTP REQUEST

```
GET /api/ticket/findAll
```

#### Description

Returns a list of all tickets

#### Produces

* `application/json`

##### Response

```json
[
  {
    "id": 64,
    "messages": [],
    "subject": "ticketTitle",
    "message": "ticketSubtitle",
    "status": "closed",
    "updatedAt": "2023-02-11T17:08:38.067468800",
    "createdAt": "2023-02-11T17:08:38.067468800"
  },
  {
    "id": 65,
    "messages": [],
    "subject": "ticketTitle",
    "message": "ticketSubtitle",
    "status": "open",
    "updatedAt": "2023-02-11T17:13:05.568306800",
    "createdAt": "2023-02-11T17:13:05.568306800"
  }
]
```

---

### HTTP REQUEST

```
DELETE /api/ticket/{ticketId}
```

#### Description

Deletes a ticket by ID

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**ticketId**  <br>*required*|Ticket ID|integer (int64)|

#### Produces

* `text/plain`

##### Response

```
Ticket has been deleted
```

---

## ChatController

---

### <a name="chat-section"></a>


### HTTP REQUEST

```
DELETE /api/chat/{messageId}
```

#### Description

Deletes a chat message by ID

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**messageId**  <br>*required*|Message ID|integer (int64)|

#### Produces

* `text/plain`

### HTTP response

##### Response

```
Message deleted
```

---

### HTTP REQUEST

```
POST /api/chat/{ticketId}
```

#### Description

Sends a chat message for the specified ticket

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Content-Type**  <br>*optional*||enum (application/json)|
|**Path**|**ticketId**  <br>*required*|Ticket ID|integer (int64)|
|**Body**|**body**  <br>*required*||[ChatMessageDto](#chatmessagedto)|

#### Consumes

* `application/json`

#### Produces

* `text/plain`

##### Request header

```
'Cookie': 'JSESSIONID=${session.cookies}'
```

##### Request body

```json
{
  "admin": true,
  "message": "message",
  "createdAt": "date"
}
```

##### Response

---

### HTTP REQUEST

```
GET /api/chat/{ticketId}
```

#### Description

Returns a list of all chat messages for the specified ticket

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**ticketId**  <br>*required*|Ticket ID|integer (int64)|

#### Produces

* `application/json`

##### Response

```json
[
  {
    "id": 155,
    "message": "Message from user",
    "createdAt": "2023-02-11T21:52:48.287030400",
    "admin": false
  },
  {
    "id": 156,
    "message": "Message from admin",
    "createdAt": "2023-02-11T21:53:03.850734400",
    "admin": true
  }
]
```
---

## AdminController

---



### <a name="admin-section"></a>

### HTTP REQUEST

```
GET /admin/panel/budget/findAll/{userId}
```

#### Description

Returns a list of all budgets for a specified user ID

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|User ID|integer (int64)|

#### Produces

* `application/json`

##### Response

```json
{
  "id": "budgetId",
  "budgetType": "INCOME",
  "incomeCategory": "INVESTMENTS",
  "expenseCategory": null,
  "value": 2000,
  "historyDayNumber": "2023-02-12"
}
```

---

### HTTP REQUEST

```
GET /admin/panel/user/find/{id}
```

#### Description

Returns a user with the specified ID

#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|User ID|integer (int64)|

#### Produces

* `application/json`

##### Response

```json
{
  "id": 339,
  "login": "admin",
  "password": "$2a$10$NcApuapqkX3S1wzRrsRUZub1f9AG87oppeaOnzkE0yivzmGlbS5YS",
  "role": "ADMIN",
  "tickets": [
    "tickets"
  ],
  "messages": [
    "messages"
  ],
  "budget": [
    "budget"
  ]
}
```

---

### HTTP REQUEST

```
GET /admin/panel/user/findAll
```

#### Description

Returns a list of all users

#### Produces

* `application/json`

##### Response

```json
[
  {
    "id": 339,
    "login": "admin",
    "password": "$2a$10$NcApuapqkX3S1wzRrsRUZub1f9AG87oppeaOnzkE0yivzmGlbS5YS",
    "role": "ADMIN",
    "tickets": [
      "tickets"
    ],
    "messages": [
      "messages"
    ],
    "budget": [
      "budget"
    ]
  },
  {
    "id": 340,
    "login": "user",
    "password": "$2a$10$NcApuapqkX3S1wzRrsRUZub1f9AG87oppeaOnzkE0yivzmGlbS5YS",
    "role": "USER",
    "tickets": [
      "tickets"
    ],
    "messages": [
      "messages"
    ],
    "budget": [
      "budget"
    ]
  }
]
```





