# Kafka Ride Matching Demo

## Overview

This project demonstrates a **simple event-driven ride matching system** using **Spring Boot microservices and Apache Kafka**.

Three services are involved:

* Rider Service
* Driver Service
* Notification Service

Instead of services calling each other using REST APIs, they communicate through **Kafka events**.

---

# Architecture

System Flow

User requests ride
→ Rider Service publishes event
→ Kafka topic `ride-requested`
→ Driver Service consumes event
→ Driver assigned
→ Kafka topic `driver-assigned`
→ Rider Service and Notification Service receive update

Flow Diagram

Frontend
→ Rider Service
→ Kafka Topic: ride-requested
→ Driver Service
→ Kafka Topic: driver-assigned
→ Rider Service & Notification Service

---

# Services

## Rider Service

Responsibilities

* Accept ride request from user
* Publish `ride-requested` event
* Listen to `driver-assigned` event
* Display assigned driver information

### Ride Request Producer

```java
kafkaTemplate.send("ride-requested", rideEventDTO);
```

### Ride Assignment Consumer

```java
@KafkaListener(topics = "driver-assigned", groupId = "ride-group")
public void driverAssigned(RideEventDTO event)
```

When a driver is assigned, the rider service prints:

```
Ride Service Updated Status
Ride ID: 123
Driver Assigned: 45
```

---

## Driver Service

Responsibilities

* Consume ride requests
* Assign a driver
* Publish driver assignment event

### Ride Request Consumer

```java
@KafkaListener(topics = "ride-requested", groupId = "driver-group")
```

Driver assignment logic

```java
rideEventDTO.setDriverId((long) (Math.random() * 100));
rideEventDTO.setStatus(EnumConstants.RideStatus.DRIVER_ASSIGNED);
```

Driver Service then publishes

```java
kafkaTemplate.send("driver-assigned", rideEventDTO);
```

---

## Notification Service

Responsibilities

* Listen for ride events
* Notify user about ride updates

### Ride Request Listener

```java
@KafkaListener(topics = "ride-requested",groupId = "notification-group")
```

Output

```
Notification: Searching for driver...
```

### Driver Assignment Listener

```java
@KafkaListener(topics = "driver-assigned",groupId = "notification-service")
```

Output

```
Notification: Driver assigned for ride 123
```

---

# Kafka Topics Used

ride-requested
driver-assigned

Topic Purpose

ride-requested
Event published when a user requests a ride.

driver-assigned
Event published when a driver is assigned.

---

# Example Event Flow

User requests ride

Event published

```
{
 "rideId":101,
 "userId":45,
 "pickupLocation":"Airport",
 "dropLocation":"City Center",
 "status":"RIDE_REQUESTED"
}
```

Driver service consumes event and assigns driver

```
{
 "rideId":101,
 "userId":45,
 "driverId":32,
 "status":"DRIVER_ASSIGNED"
}
```

Notification service and rider service receive this event.

---

# Kafka Infrastructure

Docker Compose is used to run Kafka and Zookeeper.

### docker-compose.yml

Services started

Zookeeper → port 2181
Kafka → port 9092

Start Kafka

```
docker-compose up -d
```

Verify containers

```
docker ps
```

---

# Running the Application

Step 1
Start Kafka and Zookeeper

```
docker-compose up -d
```

Step 2
Start services

Run Rider Service
Run Driver Service
Run Notification Service

Step 3
Send ride request

Example API

```
POST /rides/request
```

Request Body

```
{
 "userId":45,
 "pickupLocation":"Airport",
 "dropLocation":"City Center"
}
```

---

# Console Output Example

Rider Service

```
Ride request published
Driver assigned for ride 101
```

Driver Service

```
Driver assigned for ride 101
```

Notification Service

```
Notification: Searching for driver...
Notification: Driver assigned for ride 101
```

---

# Key Concepts Demonstrated

Event-driven microservices
Producer-consumer communication
Loose coupling between services
Real-time message processing with Kafka

---

# Future Improvements

Add database for rides and drivers
Add real driver matching algorithm
Add WebSocket for real-time user updates
Add Redis for driver location lookup
Add retry logic for failed driver assignments

---

# Summary

This project demonstrates how **Apache Kafka enables asynchronous communication between microservices**.

Instead of direct service-to-service REST calls, services publish and consume events through Kafka topics, allowing scalable and loosely coupled architecture.
