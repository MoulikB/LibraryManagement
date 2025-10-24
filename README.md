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
    -description: String
    -mediaAvailable: List<MediaInterface>
    -map: Map
    -resources: List<Resource>
    +Library(name: String)
    +addDescription(description: String): void
    +addMedia(media: MediaInterface): void
    +showMedia(mediaID: int): MediaInterface
    +removeMedia(mediaID: int): void
    +printMap(): void
    +getResources(): List<Resource>
    +addResource(resource: Resource): void
    +showResource(resourceName: String): void
    +getName(): String
}

class Map {
    -map: char[][]
    +Map(library: Library)
    +library: Library
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
    +getMediaID(): int
    +addCopies(): void
    +addReview(review: Review): void
    +getReviews(): List<Review>
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
    +Book(title: String, author: String, publisher: String, genre: MediaGenres, isbn: int, library: Library)
    +getMediaType(): String
    +getCreator(): String
    +getMediaGenre(): MediaGenres
    +borrowMedia(): boolean
    +returnMedia(): void
    +getTitle(): String
    +getMediaID(): int
    +addCopies(): void
}

class Movie {
    -title: String
    -director: String
    -mediaID: int
    -library: Library
    -genre: MediaGenres
    -totalCopies: int
    +Movie(title: String, director: String, mediaID: int, library: Library, genre: MediaGenres)
    +getMediaType(): String
    +getCreator(): String
    +getMediaGenre(): MediaGenres
    +borrowMedia(): boolean
    +returnMedia(): void
    +getTitle(): String
    +getMediaID(): int
    +addCopies(): void
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
    -itemsIssued: List<int>
    -reviewsWritten: List<Review>
    +User(username: String, id: int)
    +addReview(review: Review): void
    +getReviews(): List<Review>
    +getUsername(): String
    +getID(): int
    +equals(other: Object): boolean
}

class UserManagement {
    -users: List<User>
    +UserManagement()
    +addUser(user: User): void
    +removeUser(id: int): void
    +userExists(id: int): User
    +getUser(id: int): User
    +getUsers(): String
    +reset(): void
}

UserManagement "1" --> "0..*" User : manages  %% aggregation

%% ===== Libraries Collection =====
class LibraryManagement {
    -libraries: List<Library>
    +addLibrary(library: Library): void
    +getLibraries(): List<Library>
    +findLibrary(name: String): Library
    +reset(): void
}

LibraryManagement "1" --> "0..*" Library : catalogs

%% ===== Bookable Resources =====
class Resource {
  <<interface>>
  +getResourceName(): String
  +isAvailable(timeSlot: TimeSlot): boolean
  +addBooking(booking: Booking): void
}

class Booking {
    -resource: Resource
    -user: User
    -timeSlot: TimeSlot
    +Booking(resource: Resource, user: User, timeSlot: TimeSlot)
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
}

class TimeSlot {
  <<utility>>
  +ONE_HOUR_SLOTS: List<String>
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

%% ===== Invariants =====
note for Library "Invariant properties:\n<ul>\n<li>name != null</li>\n<li>description != null</li>\n<li>mediaAvailable != null</li>\n<li>resources != null</li>\n<li>map != null</li>\n<li>all media and resources unique</li>\n</ul>"

note for Book "Invariant properties:\n<ul>\n<li>title != null</li>\n<li>author != null</li>\n<li>publisher != null</li>\n<li>library != null</li>\n<li>genre != null</li>\n<li>totalCopies >= 0</li>\n</ul>"

note for Movie "Invariant properties:\n<ul>\n<li>title != null</li>\n<li>director != null</li>\n<li>library != null</li>\n<li>genre != null</li>\n<li>totalCopies >= 0</li>\n</ul>"

note for Review "Invariant properties:\n<ul>\n<li>user != null</li>\n<li>media != null</li>\n<li>comment != null</li>\n<li>stars between 1 and 10</li>\n</ul>"

note for Booking "Invariant properties:\n<ul>\n<li>resource != null</li>\n<li>user != null</li>\n<li>timeSlot != null</li>\n<li>timeSlot ∈ TimeSlot.ONE_HOUR_SLOTS</li>\n</ul>"

note for Computer "Invariant properties:\n<ul>\n<li>computerId != null</li>\n<li>bookings != null</li>\n<li>library != null</li>\n<li>each timeSlot unique</li>\n</ul>"

note for StudyRoom "Invariant properties:\n<ul>\n<li>roomNumber != null</li>\n<li>bookings != null</li>\n<li>library != null</li>\n<li>each timeSlot unique</li>\n</ul>"
