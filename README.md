
---
title: Library Management System
author: Moulik Bhatia (bhatiam3@myumanitoba.ca)
date: Fall 2025
---

# Domain model

## Resources

- I researched most of my project using different public library resources like :

1. Winnipeg library : <https://www.winnipeg.ca/recreation-leisure/libraries>
2. University Of Manitoba Library : <https://umanitoba.ca/libraries/>

## Diagram

Here is the diagram for my domain model

```mermaid
classDiagram
direction LR

%% ===== Core Library Domain =====
    class Library {
        -name: String
        -description: String
        -mediaAvailable: ArrayList<MediaInterface>
        -map: Map
        -resources: ArrayList<Resource>
        +Library(name)
        +addDescription(description) void
        +addMedia(media: MediaInterface) void
        +showMedia(mediaID: int) MediaInterface
        +removeMedia(mediaID: int) void
        +printMap() void
        +getResources() ArrayList<Resource>
        +addResource(resource: Resource) void
        +showResource(resourceName: String) void
        +getName() String
        +toString() String
    }

    class Map {
        -map: char[][]
        +Map(library: Library)
        +printMap() void
        +library: Library
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
    +getMediaID() int
    +addCopies() void
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
    +Book(title, author, publisher, genre, isbn, library)
    +getMediaType() String
    +getCreator() String
    +getMediaGenre() MediaGenres
    +borrowMedia() boolean
    +returnMedia() void
    +getTitle() String
    +getMediaID() int
    +addCopies() void
    +toString() String
}

class Movie {
    -title: String
    -director: String
    -mediaID: int
    -library: Library
    -genre: MediaGenres
    -totalCopies: int
    +Movie(title, director, mediaID, library, genre)
    +getMediaType() String
    +getCreator() String
    +getMediaGenre() MediaGenres
    +borrowMedia() boolean
    +returnMedia() void
    +getTitle() String
    +getMediaID() int
    +addCopies() void
    +toString() String
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
MediaInterface <|.. Book
MediaInterface <|.. Movie
Book "1" --> "1" Library : belongs to
Movie "1" --> "1" Library : belongs to
Book "1" --> "1" MediaGenres
Movie "1" --> "1" MediaGenres
User "1" o-- "0..*" Review : writes >

%% ===== Users =====
class User {
    -username: String
    -id: int
    -finesDue: double
    -itemsIssued: ArrayList<int>
    -reviewsWritten: ArrayList<Review>
    +User(username, id)
    +addReview(review: Review) void
    +getReviews(): ArrayList<Review>
    +getUsername() String
    +getID() int
    +equals(other: Object) boolean
}

class UserManagement {
    -users: ArrayList<User>
    +UserManagement()
    +addUser(user: User) void
    +removeUser(id: int) void
    +userExists(id: int) User
    +getUser(id: int) User
    +getUsers() String
    +reset() void
}
UserManagement "1" o-- "0..*" User : manages  %% aggregation

%% ===== Libraries collection =====
class LibraryManagement {
    +libraries: ArrayList<Library>
    +addLIbrary(library: Library) void
    +getLibraries() ArrayList<Library>
    +findLibrary(name: String) Library
    +reset() void }
        

LibraryManagement "1" o-- "0..*" Library : catalogs

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
    +Booking(resource: Resource, memberName: String, timeSlot: String)
    +getResource() Resource
    +getMemberName() String
    +getTimeSlot() String
    +toString() String
}

class StudyRoom {
    -roomNumber: String
    -bookings: ArrayList<Booking>
    -library: Library
    +StudyRoom(roomNumber: String, library: Library)
    +getResourceName() String
    +isAvailable(timeSlot: String) boolean
    +addBooking(booking: Booking) void
    +getBookings() ArrayList<Booking>
}

class Computer {
    -computerId: String
    -bookings: ArrayList<Booking>
    -library: Library
    +Computer(computerId: String, library: Library)
    +getResourceName() String
    +isAvailable(timeSlot: String) boolean
    +addBooking(booking: Booking) void
    +getBookings() ArrayList<Booking>
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
note for Library "Class Invariants:
name != null && !name.isEmpty()

addMedia()
Precondition: media != null.
Postcondition: media is added to mediaAvailable.
Invariant: mediaAvailable contains only unique media objects (no duplicates).

addResource()
Precondition: resource != null.
Postcondition: Resource is added to the resources list.
Invariant: Each resource in the list has a unique name.

removeMedia()
Precondition: mediaId > 0
Postcondition: media is removed from mediaAvailable
Invariant: Each media in the list has a unique name.

showMedia()
Precondition: mediaId > 0
Postcondition: media is found and returned from mediaAvailable
Invariant: Each media in the list has a unique name.
"

note for Book "totalCopies >= 0
borrowMedia() only succeeds when totalCopies > 0 (post: totalCopies decreases by 1)
returnMedia() increases totalCopies by 1
"

note for Movie "INV-M1: totalCopies >= 0
INV-M2: borrowMedia() only succeeds when totalCopies > 0 (post: totalCopies decreases by 1)
INV-M3: returnMedia() increases totalCopies by 1
"

note for Review "INV-R1: stars in 1..10
INV-R2: user != null and media != null
"

note for User "INV-U1: id is unique across all users
INV-U2: finesDue >= 0
"

note for UserManagement "INV-UM1: addUser only when userExists(id) == null
"

note for Resource "INV-RES1: addBooking(b) only if isAvailable(b.timeSlot) == true
INV-RES1: a timeSlot appears at most once per resource
"

note for StudyRoom "INV-SR1: each timeSlot appears at most once in bookings for this room
"

note for Computer "INV-C1: each timeSlot appears at most once in bookings for this computer
"

note for Booking "INV-BK1: timeSlot ∈ TimeSlots.ONE_HOUR_SLOTS
INV-BK2: memberName is non-empty"

note for Map "Constructor
Precondition: library is not null.
Postcondition: A 2D char map is created.
Invariant: Map contains layout data associated with the library."

```

