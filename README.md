# SCB-3

How to run:
DownLoad SCB-3.jar
java -jar SCB-3.jar

Execute Curl command
curl --request POST --data-binary @file.csv  --header 'Content-Type:text/csv' --header 'Accept:text/csv' http://localhost:8080/api/v1/enrich


Improvements

Loggins, testing
Load Product mapping to database

multi-threading:
thread to start and run the serv
thread to handle request
create web socket to hadle multiple request on same connection
Secure connection to webserver using HTTPS/SSL
 
