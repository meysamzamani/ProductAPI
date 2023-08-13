# Product Management Microservice

This microservice provides capabilities for managing and searching products. It supports filtering, sorting, and grouping products based on brands.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Product Management Microservice is a part of our microservices architecture and is responsible for handling product-related operations. It provides endpoints for creating, managing, and searching products based on user-defined criteria.

## Features

- Create, update, delete, and retrieve products
- Search products based on name, price range, brand, etc.
- Group products by brand and sort within each group by price

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web

## Getting Started

To get started with this microservice, follow these steps:

1. Clone this repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Configure your database connection in `application.yml`.
4. Build and run the application.

## Usage

- Create a product: Send a POST request to `/products` with product details.
- Update a product: Send a PUT request to `/products/{productId}` with updated details.
- Delete a product: Send a DELETE request to `/products/{productId}`.
- Retrieve all products: Send a GET request to `/products`.
- Search and filter products: Send a GET request to `/products/search` with filtering parameters.

## Configuration

The application configuration is managed in `application.yml`. You can customize database settings, logging, and other application properties there.

```properties
# Example application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/productdb
spring.datasource.username=root
spring.datasource.password=root
...
