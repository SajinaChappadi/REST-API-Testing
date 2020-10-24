# REST-API-Testing
Example project for TestClub mentoring program. Building a simple RESTful API with Spark and testing the API using jUnit and Rest-Assured.

The challenge is to design, develop and test a Rest api web service for an **Online Bookstore**.

Scenario: **Online bookstore**

The main resource is **books**. A book object has attributes such as *unique id, a name, an author and a price.* 
    
```json
{
    "id": "B10001",
    "name": "Atomic Habits",
    "author": "James Clear",
    "price": "$32.50",
 }
```

### APIs for Books resources
URL – https://mybookstore.com/api/books/

1. Creating a record - Create a new book in the system

* Request header:

POST https://mybookstore.com/api/books/

```json
{
	"id": "D20001",
   	"name": "Start With Why",
   	"author": "Simon Sinek",
   	"price": "$30.00",
}
```

* Response header – 

HTTP response code – 201 Created

```json
{
	"id": "D20001",
   	"name": "Start With Why",
   	"author": "Simon Sinek",
   	"price": "$30.00",
}
```

2. Updating a record – Update the content (price) of the book object identified by book id.

* Request header:

PUT https://mybookstore.com/api/books/A10001

```json
{
   "id": "B10001",
   "name": "Atomic Habits",
   "author": "James Clear",
   "price": "$25.50",
}
```

* Response header – 

HTTP response code – 200 OK

```json
{
   "id": "B10001",
   "name": "Atomic Habits",
   "author": "James Clear",
   "price": "$25.50",
}
```

3. Deleting a record – Delete the book object identified by the book id

* Request header:

DELETE https://mybookstore.com/api/books/B10001 

* Response header – 

HTTP response code – 200 OK / 204 No Content

4. Getting a record – Return a book object

* Request header:

GET https://mybookstore.com/api/books/A10001 

* Response header – 

HTTP response code – 200 OK

```json
{
   "id": "B10001",
   "name": "Atomic Habits",
   "author": "James Clear",
   "price": "$25.50",
}
```

5. Getting several records – Return all the book objects

* Request header:

GET https://mybookstore.com/api/books

* Response header – 

HTTP response code – 200 OK

```json
[
	{
    	"id": "B10001",
   	"name": "Atomic Habits",
   	"author": "James Clear",
    	"price": "$25.50",
	},
	{
	"id": "D20001",
   	"name": "Start With Why",
   	"author": "Simon Sinek",
   	"price": "$30.00",
	}
]
```
