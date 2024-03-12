# User API

## Create User
- **HTTP Method:** POST
- **URL Path:** `/api/user/{email}`
- **Description:** Retrieves user information based on the provided email address.

### Request Parameters:
- `email` (path parameter): The email address of the user.
- `password` (query parameter): The password of the user.
- `name` (query parameter): The name of the user.

### Response Format:
- **Success Response (200 OK):**
    - Content-Type: application/json
    - Body: JSON object representing the user information.
- **Error Response (500 Internal Server Error):**
  - Content-Type: application/json
  - Body: Error message indicating that the user with the specified email address was not found.

### Example:
- **Request:** POST /api/user/johndoe@example.com?password=123&name=John%20Doe
- **Response (200 OK):**
  ```json
  {
    "email": "johndoe@example.com",
    "password": 123,
    "name": "John Doe",
    ...
  }

## Read User
- **HTTP Method:** GET
- **URL Path:** `/api/user/{email}`
- **Description:** Retrieves user information based on the provided email address.

### Request Parameters:
- `email` (path parameter): The email address of the user.

### Response Format:
- **Success Response (200 OK):**
  - Content-Type: application/json
  - Body: JSON object representing the user information.
- **Error Response (404 Not Found):**
  - Content-Type: application/json
  - Body: Error message indicating that the user with the specified email address was not found.
- **Error Response (500 Internal Server Error):**
  - Content-Type: application/json
  - Body: Error message indicating that the user with the specified email address was not found.

### Example:
- **Request:** GET /api/user/johndoe@example.com
- **Response (200 OK):**
  ```json
  {
    "email": "johndoe@example.com",
    "password": 123,
    "name": "John Doe",
    ...
  }
  
## Update User
- **HTTP Method:** PUT
- **URL Path:** `/api/user/{email}`
- **Description:** Updates user information based on the provided email address.
- **Request Parameters:**
  - `email` (path parameter): The email address of the user.
  - `password` (optional query parameter): The password of the user.
  - `name` (optional query parameter): The name of the user.

### Response Format:
- **Success Response (200 OK):**
  - Content-Type: application/json
  - Body: JSON object representing the updated user information.
- **Error Response (404 Not Found):**
  - Content-Type: application/json
  - Body: Error message indicating that the user with the specified email address was not found.

### Example:
- **Request:** PUT /api/user/johndoe@example.com?name=newName&password=newPassword
- **Response (200 OK):**
  ```json
  {
    "email": "johndoe@example.com",
    "name": "newName",
    "password": "newPassword"
  }
  
## Delete User
- **HTTP Method:** DELETE
- **URL Path:** `/api/user/{email}`
- **Description:** Deletes the user with the specified email address.
- **Request Parameters:**
  - `email` (path parameter): The email address of the user.

### Response Format:
- **Success Response (200 OK):**
  - Content-Type: application/json
  - Body: JSON object indicating that the user was successfully deleted.
  - **Error Response (404 Not Found):**
  - Content-Type: application/json
  - Body: Error message indicating that the user with the specified email address was not found.

### Example:
- **Request:** DELETE /api/user/johndoe@example.com
- **Response (200 OK):**
```
User was deleted successfully
```