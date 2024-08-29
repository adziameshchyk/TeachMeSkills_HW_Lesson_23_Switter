# Task:   
	CRUD service via Servlet.   
	Add a user, delete by ID or delete all,   
	Modification of a field for a User with a specific ID.   
	Display all information about the user if requested by ID.   
	Or display all users if the ID is not defined.   
	Do all this via jdbc  
 ---


# SWITTER Project

## Description

SWITTER is a Java-based web application designed for managing users, posts, comments, and likes. It provides comprehensive CRUD (Create, Read, Update, Delete) operations using servlets and JDBC for database interaction. This project demonstrates the functionality of a social networking service via HTTP requests.

## Key Features

- **User Management**
  - Add a new user
  - Retrieve user information by ID
  - Retrieve a list of all users
  - Update user data by ID
  - Delete a user by ID
  - Delete all users
- **Post Management**
  - Add a new post
  - Retrieve post information by ID
  - Retrieve all posts by a user
  - Update post data by ID
  - Delete a post by ID
  - Delete all posts by a user
- **Comment Management**
  - Add a new comment
  - Retrieve comment information by ID
  - Retrieve all comments for a post
  - Update comment data by ID
  - Delete a comment by ID
- **Like Management**
  - Add a like to a post
  - Retrieve the number of likes for a post
  - Delete a like from a post

## Technologies

- **Programming Language:** Java
- **Web Technology:** Jakarta Servlet
- **Database:** PostgreSQL
- **JDBC Driver:** PostgreSQL JDBC Driver
- **API Testing Tool:** [Postman](https://www.postman.com/)

## Installation and Setup

### 1. Prerequisites

- **JDK 11** or later installed
- **Apache Maven** installed
- **PostgreSQL** installed and running

### 2. Clone the Repository

```bash
git clone https://github.com/yourusername/switter.git
cd switter
```

### 3. Database Setup

Create the `switter_db` database:

```sql
CREATE DATABASE switter_db;
```

### 4. Configure Database Connection

Update the `PostgreSQLConnector` class to match your database connection settings:

```java
private DataSource createDataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5432/switter_db");
    dataSource.setUsername("yourusername");
    dataSource.setPassword("yourpassword");
    return dataSource;
}
```

### 5. Build the Project

Build the project using Maven:

```bash
mvn clean install
```

### 6. Deploy the Application

1. Start your application server (e.g., Apache Tomcat).
2. Deploy the WAR file located in the `target` directory to your server.

### 7. Access the Application

Open your browser and navigate to:

```bash
http://localhost:8080/switter
```

#### Usage

The application uses HTTP methods and endpoints for CRUD operations:

User Management Endpoints

- **POST /users** - Add a new user
- **GET /users?id={id}** - Get user by ID
- **GET /users** - Get all users
- **PUT /users** - Update user data
- **DELETE /users?id={id}** - Delete user by ID
- **DELETE /users** - Delete all users

Post Management Endpoints

- **POST /posts** - Add a new post
- **GET /posts?id={id}** - Get post by ID
- **GET /posts?userId={userId}** - Get all posts by user ID
- **PUT /posts** - Update post data
- **DELETE /posts?id={id}** - Delete post by ID
- **DELETE /posts?userId={userId}** - Delete all posts by user ID

Comment Management Endpoints

- **POST /comments** - Add a new comment
- **GET /comments?id={id}** - Get comment by ID
- **GET /comments?postId={postId}** - Get all comments for a post
- **PUT /comments** - Update comment data
- **DELETE /comments?id={id}** - Delete comment by ID

Like Management Endpoints

- **POST /likes** - Add a like to a post
- **GET /likes?postId={postId}** - Get number of likes for a post
- **DELETE /likes** - Delete a like from a post

#### Request Parameters

POST /users

- `name` (required) - User name
- `lastname` (optional) - User last name
- `login` (required) - User login
- `password` (required) - User password

PUT /users

- `id` (required) - User ID
- Same parameters as POST (optional)

POST /posts

- `userId` (required) - ID of the user creating the post
- `content` (required) - Content of the post

PUT /posts

- `id` (required) - Post ID
- Same parameters as POST (optional)

POST /comments

- `postId` (required) - ID of the post being commented on
- `userId` (required) - ID of the user creating the comment
- `content` (required) - Content of the comment

PUT /comments

- `id` (required) - Comment ID
- Same parameters as POST (optional)

POST /likes

- `postId` (required) - ID of the post being liked
- `userId` (required) - ID of the user liking the post

#### Request Examples

Add a User

```http
POST /users
Content-Type: application/x-www-form-urlencoded

name=John&lastname=Doe&login=johndoe&password=secret
```
Get User by ID

```http
  GET /users?id=1
```

Get All Users

```http
GET /users
```

Update User Data

```http
PUT /users
Content-Type: application/x-www-form-urlencoded

id=1&name=John&lastname=Doe&login=johndoe&password=newsecret
```

Delete User by ID

```http
DELETE /users?id=1
```

Delete All Users

```http
DELETE /users
```

Add a Post

```http
POST /posts
Content-Type: application/x-www-form-urlencoded

userId=1&content=Hello, world!
```
Get Post by ID

```http
GET /posts?id=1
```
Get All Posts by User ID

```http
GET /posts?userId=1
```
Update Post Data

```http
PUT /posts
Content-Type: application/x-www-form-urlencoded

id=1&userId=1&content=Updated content
```
Delete Post by ID

```http
DELETE /posts?id=1
```

Delete All Posts by User ID

```http
DELETE /posts?userId=1
```
Add a Comment

```http
POST /comments
Content-Type: application/x-www-form-urlencoded

postId=1&userId=1&content=Great post!
```
Get Comment by ID

```http
GET /comments?id=1
```
Get All Comments for a Post

```http
GET /comments?postId=1
```
Update Comment Data

```httpPUT /comments
Content-Type: application/x-www-form-urlencoded

id=1&postId=1&userId=1&content=Updated comment
```

Delete Comment by ID

```http
DELETE /comments?id=1
```

Add a Like

```httpPOST /likes
Content-Type: application/x-www-form-urlencoded

postId=1&userId=1
```

Get Number of Likes for a Post

```http
GET /likes?postId=1
```
Delete a Like

```http
DELETE /likes?postId=1&userId=1
```

### Project Structure

The project is organized into several packages:

- **com.tms.connector:** Contains the `PostgreSQLConnector` class for managing and setting up the database connection.
- **com.tms.dao:** Contains DAO classes such as `UserDAO`, `PostDAO`, `CommentDAO`, and `LikeDAO` for database operations.
- **com.tms.entity:** Contains entity classes representing the data models such as `User`, `Post`, `Comment`, and `Like`.
- **com.tms.exception:** Contains custom exceptions.
- **com.tms.listener:** Contains the `AppContextListener` class for initializing application context and dependencies.
- **com.tms.service:** Contains service classes for implementing business logic.
- **com.tms.servlet:** Contains servlets for handling HTTP requests related to user, post, comment, and like operations.

### Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature/YourFeature
   ```
3. Commit your changes:
   ```bash
   git commit -am 'Add YourFeature'
   ```
4. Push the branch:
   ```bash
   git push origin feature/YourFeature
   ```
5. Create a new Pull Request.
