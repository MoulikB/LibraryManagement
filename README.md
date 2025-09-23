
---
title: Library Management System
author: Moulik Bhatia (bhatiam3@myumanitoba.ca)
date: Fall 2025
---

# Domain model

## Resources

- I reasearched most of my project using different public library resources like :

1. Winnipeg library : <https://www.winnipeg.ca/recreation-leisure/libraries>
2. University Of Manitoba Library : <https://umanitoba.ca/libraries/>

## Diagram

Here is the diagram for my domain model

```mermaid
classDiagram
direction LR

%% ===== Core Library Domain =====
class Library {
  +name: String
  +description: String
  +mediaAvailable: ArrayList<MediaInterface>
  +resources: ArrayList<Resource>
  +map: Map
  +addMedia(media: MediaInterface) void
  +addResource(resource: Resource) void
  +toString() String
}

class Map {
  +library: Library
  +showMap(): void
}

Library "1" *-- "1" Map : layout  %% composition (Map exists for one Library)
Library "1" o-- "0..*" Resource : resources  %% aggregation
Library "1" o-- "0..*" MediaInterface : catalog  %% aggregation

%% ===== Media hierarchy =====
class MediaInterface {
  <<interface>>
  +getMediaType() String
  +getCreator() String
  +getMediaGenre() MediaGenres
  +borrowMedia() boolean
  +returnMedia() void
  +getTitle() String
  +getAvailableCopies() int
  +addReview(review: Review) void
  +getReviews() ArrayList<Review>
}

class MediaGenres {
  <<enumeration>>
  HORROR
  COMEDY
  ACTION
  ROMANCE
  THRILLER
  FICTION
  NONFICTION
}

class Book {
  -title: String
  -author: String
  -publisher: String
  -mediaID: int
  -library: Library
  -genre: MediaGenres
  -totalCopies: int
  +borrowMedia() boolean
  +returnMedia() void
  +getTitle() String
  +getAvailableCopies() int
}

class Movie {
  -title: String
  -director: String
  -mediaID: int
  -library: Library
  -genre: MediaGenres
  -totalCopies: int
  +borrowMedia() boolean
  +returnMedia() void
  +getTitle() String
  +getAvailableCopies() int
}

MediaInterface <|.. Book
MediaInterface <|.. Movie
Book "1" --> "1" Library : belongs to
Movie "1" --> "1" Library : belongs to
Book "1" --> "1" MediaGenres
Movie "1" --> "1" MediaGenres

%% ===== Reviews =====
class Review {
  <<record>>
  +user: User
  +media: MediaInterface
  +comment: String
  +stars: int
}
User "1" --> "0..*" Review : writes
MediaInterface "1" --> "0..*" Review : has

%% ===== Users =====
class User {
  -username: String
  -id: int
  -finesDue: double
  -itemsIssued: ArrayList<int>
  +getUsername() String
  +getID() int
  +payFine(amount: double) void
  +equals(other: Object) boolean
}

class UserManagement {
  -users: ArrayList<User>
  +addUser(user: User) void
  +removeUser(id: int) void
  +userExists(id: int) User?
  +getUsers() String
}
UserManagement "1" o-- "0..*" User : manages  %% aggregation

%% ===== Bookable Resources =====
class Resource {
  <<interface>>
  +getResourceName() String
  +isAvailable(timeSlot: String) boolean
  +addBooking(booking: Booking) void
}

class Booking {
  -resource: Resource
  -memberName: String
  -timeSlot: String
  +getResource() Resource
  +getMemberName() String
  +getTimeSlot() String
  +toString() String
}

class StudyRoom {
  -roomNumber: String
  -bookings: ArrayList<Booking>
  -library: Library
  +getResourceName() String
  +isAvailable(timeSlot: String) boolean
  +addBooking(booking: Booking) void
}

class Computer {
  -computerId: String
  -bookings: ArrayList<Booking>
  -library: Library
  +getResourceName() String
  +isAvailable(timeSlot: String) boolean
  +addBooking(booking: Booking) void
}

class TimeSlots {
  <<utility>>
  +ONE_HOUR_SLOTS: List<String>
}

Resource <|.. StudyRoom
Resource <|.. Computer
Booking "1" --> "1" Resource : books
StudyRoom "1" --> "0..*" Booking : maintains
Computer "1" --> "0..*" Booking : maintains
StudyRoom "1" --> "1" Library : located at
Computer "1" --> "1" Library : located at

%% ===== Invariants (annotated as notes) =====
note for Library "INV-L1: name is non-empty
INV-L2: mediaAvailable items are unique by identity (no duplicate object refs)
INV-L3: resources are unique per Library (no duplicate resource names)
"

note for Book "INV-B1: totalCopies >= 0
INV-B2: borrowMedia() only succeeds when totalCopies > 0 (post: totalCopies decreases by 1)
INV-B3: returnMedia() increases totalCopies by 1
"

note for Movie "INV-M1: totalCopies >= 0
INV-M2: borrowMedia() only succeeds when totalCopies > 0 (post: totalCopies decreases by 1)
INV-M3: returnMedia() increases totalCopies by 1
"

note for Review "INV-R1: stars in 1..5
INV-R2: user != null and media != null
"

note for User "INV-U1: id is unique across all users
INV-U2: finesDue >= 0
"

note for UserManagement "INV-UM1: addUser only when userExists(id) == null
"

note for Resource "INV-RES1: addBooking(b) only if isAvailable(b.timeSlot) == true
"

note for StudyRoom "INV-SR1: each timeSlot appears at most once in bookings for this room
"

note for Computer "INV-C1: each timeSlot appears at most once in bookings for this computer
"

note for Booking "INV-BK1: timeSlot ∈ TimeSlots.ONE_HOUR_SLOTS
INV-BK2: memberName is non-empty
"

```

