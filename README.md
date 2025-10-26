---
title: Library Management System
author: Moulik Bhatia (bhatiam3@myumanitoba.ca)
date: 24 October 2025
---

## Domain Model

This repository contains a domain model and an implementation of a **Library Management System**.  
It maintains a library and user database, manages media (books and movies), resources (computers and study rooms), and provides a booking system for reserving resources.

## Resources and References

I researched this project using publicly available library systems:

1. Winnipeg Public Library — <https://www.winnipeg.ca/recreation-leisure/libraries>
2. University of Manitoba Libraries — <https://umanitoba.ca/libraries/>


## Diagram

The following Mermaid UML diagram represents the domain model of the system.

```mermaid
classDiagram
%% ===== Core Library =====
    class Library {
    -name: String
    -mediaAvailable: List<MediaInterface>
    -map: Map
    -resources: List<Resource>
    -libraryManagement : LibraryManagement
    +Library(name: String)
    +checkInvariants() : void
    +addMedia(media: MediaInterface): void
    +getMediaAvailable() : List<MediaInterface>
    +getName(): String
    +setName(String name) : void
    +getMap() : Map
    +getResources() : List<Resource>
    +addResource(Resource resource) : void
    +getResource(String resourceName) : Resource
    +removeMedia(mediaID: int): boolean
    +showMedia(int mediaId) : MediaInterface
    
}

class Map {
        - mapGrid : char[][]
        - library : Library
        + Map(library:Library)
        + getMap() : char[][]
    }

Library "1" *-- "1" Map : layout %% composition (Map exists for one Library)
Library "1" --o "0..*" Resource : aggregation
Library "1" --o "0..*" MediaInterface : aggregation

%% ===== Media Hierarchy =====
class MediaInterface {
    <<interface>>
    +getMediaType(): String
    +getCreator(): String
    +getMediaGenre(): MediaGenres
    +borrowMedia(): boolean
    +returnMedia(): void
    +getTitle(): String
    +getAvailableCopies() : int
    +getLibrary() : Library
    +getMediaID(): int
    +addCopies(): void
    +addReview(review: Review): void
    +getReviews(): List<Review>
    +mediaExists(MediaInterface media) : boolean
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
    -reviews : ArrayList<Review>
    +Book(title: String, author: String, publisher: String, genre: MediaGenres, isbn: int, library: Library)
    +checkInvariants() : void
    +getMediaType(): String
    +getCreator(): String
    +getMediaGenre(): MediaGenres
    +borrowMedia(): boolean
    +returnMedia(): void
    +getAvailableCopies() : int
    +getPublisher() : String
    +getLibrary() : Library
    +getMediaID() : int
    +setLibrary(Library library) : void
    +mediaExists(MediaInterface media) : boolean
    +addReview(Review review) : void
    +getTitle(): String
    
}

class Movie {
    -title: String
    -director: String
    -mediaID: int
    -library: Library
    -genre: MediaGenres
    -totalCopies: int
    +Movie(title: String, director: String, mediaID: int, library: Library, genre: MediaGenres)
    -totalCopies: int
    -reviews : ArrayList<Review>
    +checkInvariants() : void
    +getMediaType(): String
    +getCreator(): String
    +getMediaGenre(): MediaGenres
    +borrowMedia(): boolean
    +returnMedia(): void
    +getAvailableCopies() : int
    +getPublisher() : String
    +getLibrary() : Library
    +getMediaID() : int
    +setLibrary(Library library) : void
    +mediaExists(MediaInterface media) : boolean
    +addReview(Review review) : void
    +getTitle(): String
}

MediaInterface <|.. Book : an instance of
MediaInterface <|.. Movie : an instance of
Book "1" --> "1" Library : belongs to
Movie "1" --> "1" Library : belongs to
Book "1" --> "1" MediaGenres : describes
Movie "1" --> "1" MediaGenres : describes

%% ===== Reviews =====
class Review {
  <<record>>
  +user: User
  +media: MediaInterface
  +comment: String
  +stars: int
}

User "1" -- "0..*" Review : writes >

%% ===== Users =====
class User {
    -username: String
    -id: int
    -finesDue: double
    -email : String
    -phone : int
    -itemsIssued: List<Integer>
    -reviewsWritten: List<Review>
    +User(username: String, id: int , email : String , phone : int)
    +checkInvariants() : void
    +getID() : int
    +getUsername() : String
    +getEmail() : String
    +getPhone() : int
    +addReview(review: Review): void
    +getReviews(): List<Review>
    +equals(User otherUser) : boolean
    +getItemsIssued() : List<Integer>
    +equals(User otherUser): boolean
}

class UserManagement {
    -users: List<User>
    +UserManagement()
    +checkInvariants() : void
    +addUser(user: User): void
    +removeUser(id: int): void
    +userExists(id: int): User
    +userExistsBoolean(int id) : boolean
    +getUser(id: int): User
    +getUsers(): String
    +reset(): void
}

UserManagement "1" --> "0..*" User : manages  %% aggregation

%% ===== Libraries Collection =====
class LibraryManagement {
    -libraries: List<Library>
    +LibraryManagement()
    +checkInvariants() : void
    +addLibrary(library: Library): void
    +getLibraries(): List<Library>
    +findLibrary(name: String): Library
    +reset(): void
}

LibraryManagement "1" --> "0..*" Library : catalogs

%% ===== Bookable Resources =====
class Resource {
  <<interface>>
  +getResourceName(): Resource
  +isAvailable(timeSlot: TimeSlot): boolean
  +addBooking(booking: Booking): void
}

class Booking {
    -resource: Resource
    -user: User
    -timeSlot: TimeSlot
    +Booking(resource: Resource, user: User, timeSlot: TimeSlot)
    +checkInvariants() : void
    +getResource(): Resource
    +getUser(): User
    +getTimeSlot(): TimeSlot
}

class StudyRoom {
    -roomNumber: String
    -bookings: List<Booking>
    -library: Library
    +StudyRoom(roomNumber: String, library: Library)
    +getResourceName(): String
    +isAvailable(timeSlot: TimeSlot): boolean
    +addBooking(booking: Booking): void
    +getBookings(): List<Booking>
}

class Computer {
    -computerId: String
    -bookings: List<Booking>
    -library: Library
    +Computer(computerId: String, library: Library)
    +getResourceName(): String
    +isAvailable(timeSlot: TimeSlot): boolean
    +addBooking(booking: Booking): void
    +getBookings(): List<Booking>
    +getLibrary() : Library
}

class TimeSlot {
  <<enumeration>>
   NINE_TO_TEN("09:00–10:00"),
   TEN_TO_ELEVEN("10:00–11:00"),
   ELEVEN_TO_TWELVE("11:00–12:00"),
   TWELVE_TO_ONE("12:00–13:00"),
   ONE_TO_TWO("13:00–14:00"),
   TWO_TO_THREE("14:00–15:00"),
   THREE_TO_FOUR("15:00–16:00"),
   FOUR_TO_FIVE("16:00–17:00")
   -label : String
   +TimeSlots(String label) 
   +getLabel() : String
   +toString() : String
   +fromString(String input) : TimeSlots
}

class PrintLibrary {
    +printLibrary(Library library) : void
    +printLibraryList() : void
    +printMap(Library library) : void
}

class PrintMedia {
    +printMovie(Movie movie) : void
    +printBook(Book book) : void
}

class PrintUser {
    +userInfo(User user) : void
    +printALlUsers() : void
}

class PrintReview {
    +printReview(Review review) : void
    +printReviews(MediaInterface media) : void
}

class PrintResource {
    +printResource(Resource resource) : void
}

class PrintMap {
    +printMap(Map inputMap)
}

Resource <|.. StudyRoom
Resource <|.. Computer
Booking "1" --> "1" Resource : books
Booking "1" --> "1" User : madeBy
Booking "1" --> "1" TimeSlot : scheduledAt
StudyRoom "1" <-- "0..*" Booking : maintains
Computer "1" <-- "0..*" Booking : maintains
StudyRoom "1" <-- "1" Library : locatedAt
Computer "1" <-- "1" Library : locatedAt

PrintLibrary ..> Library
PrintMedia ..> MediaInterface
PrintUser ..> User
PrintReview ..> Review
PrintResource ..> Resource
PrintMap ..> Map

%% ===== Invariants =====
note for Library "Invariant properties:<ul><li>name != null</li><li>mediaAvailable != null</li><li>resources != null</li><li>map != null</li><li>all media and resources unique</li></ul>"
note for User "Invariant properties:<ul><li>id > 0</li><li>name != null</li><li>borrowedMedia != null</li><li>bookings != null</li><li>reviews != null</li><li>no null elements in lists</li></ul>"
note for Book "Invariant properties:<ul><li>title != null</li><li>author != null</li><li>genre != null</li><li>isbn > 0</li><li>publisher != null</li><li>copies >= 0</li></ul>"
note for Movie "Invariant properties:<ul><li>title != null</li><li>director != null</li><li>genre != null</li><li>rating between 0 and 10</li><li>copies >= 0</li></ul>"
note for Resource "Invariant properties:<ul><li>name != null</li><li>bookings != null</li><li>no duplicate (user, timeSlot) pairs</li></ul>"
note for Booking "Invariant properties:<ul><li>resource != null</li><li>user != null</li><li>timeSlot != null</li></ul>"
note for Review "Invariant properties:<ul><li>user != null</li><li>media != null</li><li>comment != null</li><li>stars between 1 and 10</li></ul>"
note for Map "Invariant properties:<ul><li>mapGrid != null</li><li>library != null</li></ul>"
