# Senior Capstone - WVU PRT Monitoring Project

# CSEE481 - Spring 2024

## Programmers

Marissa Wahler,  
Ashwin Kannan

## Introduction

This is for our senior capstone project, which is designed to improve the energy efficiency and performance of the West
Virginia University's Personal Rapid Transit (PRT) System. The main focus of this project is to collect data regarding
the energy consumption of the PRT. Additional sensors are to be added to measure the GPS location and temperature. These
sensors will collect real time information, which can then be studied and analyzed to detect patterns that may be useful
for improving the functionality of the PRT system.

Our implementation this semester focused on creating a server and client and a database to store the collected data
points. The server and client are designed to be extensible so that more of these sensors and Pis can be added to the
system in the future.

## Dependencies

Running Docker

Running local Kubernetes cluster (this comes with Docker, if on Docker Desktop)

Java JDK installed

## To Build and Run the Server

Run the following commands in the terminal

```bash
cd PRT-Capstone-Project/PRTServer
./gradlew build buildImage
kubectl apply -f PRTWebServer.yaml
```

Then, the server will be accessible at these two websites:

[Swagger UI](http://localhost:30080/swagger-ui/index.html)
: This is for Rest API documentation and testing.

[PRT Database](http://localhost:30081/db/prtdatabase)
: This is for viewing the schemas and the data.

## To Build and Run the Client Locally

Note: These steps are similar on the Pi.

```bash
./gradlew build jar shadowJar
java -jar build/libs/PRTClient-1.0-SNAPSHOT-all.jar
```

## To Build and Run the Client on the Raspberry Pi

Locally:

```bash
./gradlew build jar shadowJar
scp build/libs/PRTClient-1.0-SNAPSHOT-all.jar prt@<Pi IP Address>:~
ssh prt@<PI IP Address>
#You are now on the Pi
export SERVER_IP=<Server IP Address>
java -jar build/libs/PRTClient-1.0-SNAPSHOT-all.jar
```

The client and server Java code will be located in this Github repository.

## Future Work

Future students have several options for continuing work on this project, including: working on the user interface to
make the site more aesthetic and easier to navigate, implementing user authentication features such as login credentials
and improving the overall security of the project, or installing additional sensors to measure any other variables that
may be of use.  
