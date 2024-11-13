# Product Catalog API
This project is a Products Catalog API built with Kotlin, Spring Boot, and Gradle. It provides a set of RESTful APIs for managing products, including features like pagination, sorting, and category filtering.  
## Features
- Retrieve a list of products with pagination and sorting.
- Filter products by category.
- Apply discounts based on product category and SKU.
- API documentation with OpenAPI and Swagger.
## Getting Started
1. **Clone the repository:**
```sh
git clone https://github.com/yourusername/products-api.git
```
2. **Build the project using Gradle:**
```sh
./gradlew clean build
```
3. **Run the app:**
```sh
./gradlew bootRun
```

or by executing in the IDE:  
- Open the project in IntelliJ IDEA.
- Build the project using the Gradle tool window.
- Run the main application class.

## API Database Access
Access the H2 database console at: http://localhost:8080/h2-console.
(You'll need to have the project running to access the database console.)

## API Documentation
GET /products

**Description:**
Retrieves a list of products with pagination and sorting.

**Request Parameters:**
- `page` (optional): The page number to retrieve. Default: 0.
- `size` (optional): The number of products per page. Default: 20.
- `sort` (optional): The field to sort by. ('sku', 'price', 'description', 'category')
- `category` (optional): The category to filter by.

**Example Request:**
```sh
curl -X GET "http://localhost:8080/products?page=0&size=10&sort=name,asc&category=electronics" -H "accept: application/json"
```

**Responses:**
- **200**: Successfully retrieved the list of products.
    - Body:
      ```json
      {
        "content": [
          {
            "id": 1,
            "name": "Laptop",
            "category": "electronics",
            "price": 999.99
          },
          {
            "id": 2,
            "name": "Smartphone",
            "category": "electronics",
            "price": 599.99
          }
        ],
        "page": 0,
        "size": 10,
        "totalElements": 2
      }
      ```
- **404**: No products found for the given parameters.
- **500**: Internal Server Error.
