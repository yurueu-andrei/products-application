# Functionality:

- ### you have CRUD operations for Discount cards and Products
- ### CRUD operations for Products are cached (cache configuration tips below)
- ### you can generate cheque using the following URL:

> http://localhost:8080/cheque?[arguments]

## Arguments template:

> productId=quantity&productId=quantity...&card=17

# In order to configure cache you need:

- ### set up cache.algorithm(only LRU or LFU possible) property in application.yml
- ### set up cache.size(positive number) property in application.yml
- ### cache will not be created in case of absence of described properties

# Before running the application, you should know:

- ### there are 100 discount cards, their IDs are from 1 to 100
- ### there are 10 items, their IDs are from 1 to 10
- ### products are validated by barcode field (example: 111AAA11AA)

## In order to build the jar you should run the following command:

> ./gradlew clean build -x test

## In order to run application you should run the following command:

> docker-compose up

## To run tests:

> ./gradlew test

# You can find console and file application on other branches