# Before running the application, you should know: 
- ### there are 51 discount cards, their numbers are from 1000 to 1050
- ### there are 10 items, their IDs are from 1 to 10
- ### arguments should be written to arguments.txt file in root
- ### cheques will be stored in cheque.txt file (in case of absence will be created automatically)

## In order to compile the application you should run the following command:

>javac -sourcepath ./src -d bin src/main/java/by/clevertec/cheque/*.java

## In order to run application you should run the following command:

>java -classpath ./bin by.clevertec.cheque.ChequeRunner