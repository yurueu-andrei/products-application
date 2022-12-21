# Functionality:
- ### you have CRUD operations for Discount cards and Products
- ### you can generate cheque using the following URL:
> http://localhost:8080/cheque?[arguments]
## Arguments template:
> productId=quantity&productId=quantity...&card=17


# Before running the application, you should know: 
- ### there are 100 discount cards, their IDs are from 1 to 100
- ### there are 10 items, their IDs are from 1 to 10

## In order to build the jar you should run the following command:

>./gradlew clean build -x test

## In order to run application you should run the following command:

>docker-compose up

## To run tests: Gradle -> verification -> test