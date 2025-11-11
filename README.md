
---
title: Library Management System
author: Moulik Bhatia (bhatiam3@myumanitoba.ca)
date: 30 September 2025
---

# Domain model
This repository contains a domain model and an initial implementation of our Library System.
It maintains a simple library and user database management and manages users, media (books and movies) along with resources (computers and study rooms) and a booking system for said resources.

## Resources

- I researched most of my project using different public library resources like :

1. Winnipeg library : <https://www.winnipeg.ca/recreation-leisure/libraries>
2. University Of Manitoba Library : <https://umanitoba.ca/libraries/>

## Diagram

Our domain model is a UML class diagram drawn using Mermaid.

```mermaid
classDiagram
%% ===== Core Library =====
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
Library "1"  --o "0..*" Resource : resources  %% aggregation
Library "1" --o "0..*" MediaInterface : catalog  %% aggregation

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
  +toString() : String
}

User "1" -- "0.." Review : writes >

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
UserManagement "1" --> "0..*" User : manages  %% aggregation

%% ===== Libraries collection =====
class LibraryManagement {
    +libraries: ArrayList<Library>
    +addLIbrary(library: Library) void
    +getLibraries() ArrayList<Library>
    +findLibrary(name: String) Library
    +reset() void }
        

LibraryManagement "1" --> "0..*" Library : catalogs

%% ===== Bookable Resources =====
class Resource {
  <<interface>>
  +getResourceName() String
  +isAvailable(timeSlot: String) boolean
  +addBooking(booking: Booking) void
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


%% ===== Resource & Booking Management =====
    class Booking {
        - resource: Resource
        - user: User
        - timeSlot: TimeSlot
        + Booking(resource: Resource, user: User, timeSlot: TimeSlot)
        + getResource(): Resource
        + getUser(): User
        + getTimeSlot(): TimeSlot
    }

    class BookResource {
        - booking: Booking
        - static bookings: List~BookResource~
        + BookResource(booking: Booking)
        + checkBooking(): void
    }

    class BorrowMedia {
        + issueUser(media: MediaInterface, user: User)
    }

    class Waitlist {
        + waitlistUser(media: MediaInterface, user: User): void
    }

    class TimeSlotSearch {
        - MAX_DAYS_AHEAD: int
        + viewNextTwoWeeks(resource: Resource): List~String~
        + viewInRange(resource: Resource, start: LocalDate, end: LocalDate): List~String~
        + nextXAvailable(resource: Resource, afterTime: LocalTime, x: int): List~String~
        - parseStartTime(slot: TimeSlots): LocalTime
    }

    TimeSlotSearch --> Resource : checks
    TimeSlotSearch --> TimeSlots : uses




    Waitlist "1" --> "1" MediaInterface : manages
    Waitlist "1" --> "1" User : addsTo



    BookResource "1" --> "1" Booking : registers
    BorrowMedia "1" --> "1" MediaInterface : uses
    BorrowMedia "1" --> "1" User : issuesTo
    Booking "1" --> "1" Resource : books
    Booking "1" --> "1" User : madeBy




Resource <|.. StudyRoom
Resource <|.. Computer
Booking "1" --> "1" Resource : books
StudyRoom "1" <-- "0..*" Booking : maintains
Computer "1" <-- "0..*" Booking : maintains
StudyRoom "1" <-- "1" Library : located at
Computer "1" <-- "1" Library : located at

%% ===== User Interface (UI) Layer =====
    class Kiosk {
        - user: User
        - library: Library
        + main(args: String[]): void
        - runKiosk(): void
        - showWelcomeScreen(): void
        - showUserMenu(): void
        - logout(): void
        - browseMedia(): void
        - borrowMedia(): void
        - returnMedia(): void
        - viewResources(): void
        - bookResource(): void
        - handleBooking(resource: Resource, date: LocalDate): void
        - handleFutureBooking(resource: Resource): void
        - showDailyAvailability(resource: Resource): void
        - showTwoWeekAvailability(resource: Resource): void
        - showNextXAfterTime(resource: Resource): void
        - showRangeAvailability(resource: Resource): void
        - findPathOnMap(): void
        - promptMenu(options: String[]): int
    }

    class LogIn {
        + loginUser(): User
        - isInvalid(input: String): boolean
    }

    class RegisterUser {
        + registerUser(): User
    }

    Kiosk "1" --> "1" Library : accesses
    Kiosk "1" --> "1" User : activeUser
    Kiosk "1" --> "1" BorrowMedia : uses
    Kiosk "1" --> "1" Waitlist : uses
    Kiosk "1" --> "1" BookResource : uses
    Kiosk "1" --> "1" LogIn : uses
    Kiosk "1" --> "1" RegisterUser : uses
    Kiosk "1" --> "1" Resource : books
    LogIn "1" --> "1" UserManagement : validates
    RegisterUser "1" --> "1" UserManagement : registers

%% ===== Pathfinding Subsystem =====
    class PathFinder {
        - PATH_CHAR: char
        - FLOOR_CHAR: char
        - START_CHAR: char
        - library: Library
        - map: char[][]
        + PathFinder(library: Library)
        + runForTarget(targetChar: char): boolean
        - dfsAndMarkPath(start: Coordinate, target: Coordinate): boolean
        - inBounds(r: int, c: int, rows: int, cols: int): boolean
        - isWalkable(r: int, c: int, rows: int, cols: int, visited: boolean[][], target: Coordinate): boolean
        - findChar(ch: char): Coordinate
        + clearPath(): void
        + printMap(): void
    }

    class Coordinate {
        - x: int
        - y: int
        + Coordinate(x: int, y: int)
        + getX(): int
        + getY(): int
        + equals(other: Coordinate): boolean
    }

    class Stack {
<<interface>>
+ push(item: T): void
+ pop(): Object
+ size(): int
+ isEmpty(): boolean
+ peek(): Object
}

class LinkedListStack {
- top: Node<T>
- size: int
+ push(item: T): void
+ pop(): T
+ size(): int
+ isEmpty(): boolean
+ peek(): T
}

class EmptyStackException {
+ EmptyStackException(message: String)
}

PathFinder --> Library : uses
PathFinder --> Coordinate : uses
PathFinder --> LinkedListStack : uses
LinkedListStack --> Stack : implements
LinkedListStack --> EmptyStackException : throws



%% ===== Invariants  =====
note for Library "Invariant properties:\n<ul>\n    
<li>name != null</li>\n    
<li>name.length() > 0</li>\n    
<li>description != null</li>\n    
<li>mediaAvailable != null</li>\n    
<li>resources != null</li>\n    
<li>map != null</li>\n    
<li>loop: all mediaAvailable items are unique (no duplicate object references)</li>\n    
<li>loop: all resources have unique names</li>\n</ul>"

note for Map "Invariant properties:\n<ul>\n    
<li>map != null</li>\n    
<li>library != null</li>\n</ul>"

note for Book "Invariant properties:\n<ul>\n    
<li>title != null</li>\n   
<li>author != null</li>\n    
<li>publisher != null</li>\n    
<li>library != null</li>\n    
<li>genre != null</li>\n    
<li>totalCopies >= 0</li>\n</ul>"

note for Movie "Invariant properties:\n<ul>\n    
<li>title != null</li>\n    
<li>director != null</li>\n    
<li>library != null</li>\n    
<li>genre != null</li>\n   
 <li>totalCopies >= 0</li>\n</ul>"

note for Review "Invariant properties:\n<ul>\n    
<li>user != null</li>\n    
<li>media != null</li>\n    
<li>comment != null</li>\n    
<li>stars >= 1 && stars <= 10</li>\n</ul>"

note for User "Invariant properties:\n<ul>\n    
<li>username != null</li>\n    
<li>username.length() > 0</li>\n    
<li>id > 0</li>\n   
<li>finesDue >= 0</li>\n    
<li>itemsIssued != null</li>\n    
<li>loop: all issued item IDs are valid</li>\n</ul>"

note for UserManagement "Invariant properties:\n<ul>\n    
<li>users != null</li>\n    
<li>loop: all user IDs are unique</li>\n</ul>"

note for LibraryManagement "Invariant properties:\n<ul>\n    
<li>libraries != null</li>\n    
<li>loop: all libraries have unique names</li>\n</ul>"

note for StudyRoom "Invariant properties:\n<ul>\n    
<li>roomNumber != null</li>\n    
<li>bookings != null</li>\n    
<li>library != null</li>\n   
<li>loop: each timeSlot appears at most once in bookings</li>\n</ul>"

note for Computer "Invariant properties:\n<ul>\n    
<li>computerId != null</li>\n    
<li>bookings != null</li>\n    
<li>library != null</li>\n    
<li>loop: each timeSlot appears at most once in bookings</li>\n</ul>"

note for Booking "Invariant properties:\n<ul>\n    
<li>resource != null</li>\n    
<li>memberName != null && memberName.length() > 0</li>\n    
<li>timeSlot != null</li>\n    
<li>timeSlot ∈ TimeSlots.ONE_HOUR_SLOTS</li>\n</ul>"

note for TimeSlots "Invariant properties:\n<ul>\n    
<li>ONE_HOUR_SLOTS != null</li>\n    
<li>loop: all time slots are valid and unique</li>\n</ul>"

``` 

The following Mermaid diagram shows the user flow for the `Kiosk` interface —  
from login to browsing, borrowing, booking, and pathfinding.

```mermaid

flowchart TD

%% ========= WELCOME SCREEN =========
subgraph WELCOME["WELCOME / LOGIN / REGISTER"]
    start[[Start Kiosk]]
    login[[Log In]]
    register[[Register New User]]
    login_result{Valid credentials?}
    register_result{Registration successful?}
    home[[Main Menu]]
    exit[[Exit Program]]

    start --> login
    start --> register
    login --> login_result
    register --> register_result
    login_result -- Yes --> home
    login_result -- No --> login
    register_result -- Yes --> home
    register_result -- No --> register
    start --> exit
end

%% ========= MAIN MENU =========
subgraph MAIN["USER MAIN MENU"]
    home --> browse[[Browse Media]]
    home --> borrow[[Borrow Media]]
    home --> returnm[[Return Media]]
    home --> viewres[[View Resources]]
    home --> bookres[[Book Resource]]
    home --> map[[Find Path on Map]]
    home --> logout[[Log Out]]
end

%% ========= MEDIA BROWSING =========
subgraph BROWSE_MEDIA["Browse Media Options"]
    browse --> all[[Browse All Media]]
    browse --> movies[[Browse All Movies]]
    browse --> books[[Browse All Books]]
    browse --> director[[View by Director]]
    browse --> author[[View by Author]]
    browse --> title[[Search by Title]]
    browse --> back1[[Back to Main Menu]]
    back1 --> return_browse[[Return to Main Menu]]
    return_browse --> home
end

%% ========= BORROWING MEDIA =========
subgraph BORROW_MEDIA["Borrow Media"]
    borrow --> find_media[[Enter Media ID]]
    find_media --> available{Available?}
    available -- Yes --> borrow_success[[Borrow Success]]
    available -- No --> waitlist_choice{Join Waitlist?}
    waitlist_choice -- Yes --> waitlist_added[[Added to Waitlist]]
    waitlist_choice -- No --> waitlist_declined[[Not Added to Waitlist]]
    borrow_success --> post_borrow[[Return to Menu]]
    waitlist_added --> post_borrow
    waitlist_declined --> post_borrow
    post_borrow --> return_borrow[[Return to Main Menu]]
    return_borrow --> home
end

%% ========= RETURN MEDIA =========
subgraph RETURN_MEDIA["Return Media"]
    returnm --> show_borrowed[[Show Borrowed Media]]
    show_borrowed --> enter_id[[Enter Media ID]]
    enter_id --> confirm_return[[Return Media]]
    confirm_return --> review_prompt[[Leave a Review?]]
    review_prompt -- Yes --> review_yes[[Add Comment and Rating]]
    review_prompt -- No --> skip_review[[Skip Review]]
    review_yes --> review_done[[Review Submitted]]
    review_done --> return_return[[Return to Main Menu]]
    skip_review --> return_return
    return_return --> home
end

%% ========= RESOURCES & BOOKINGS =========
subgraph BOOKING["Book Library Resources"]
    bookres --> resource_name[[Enter Resource Name]]
    resource_name --> exists{Resource Exists?}
    exists -- No --> return_booking[[Return to Main Menu]]
    exists -- Yes --> resource_menu[[Resource Options]]

    resource_menu --> today[[Book for Today]]
    resource_menu --> future[[Book Future Date <= 2 Weeks]]
    resource_menu --> two_week[[View All Available 2 Weeks]]
    resource_menu --> nextx[[Next X After Time]]
    resource_menu --> range[[View in Date Range]]
    resource_menu --> back2[[Back to Main Menu]]

    today --> confirm_today[[Confirm Booking]]
    future --> confirm_future[[Confirm Booking]]
    confirm_today --> return_booking
    confirm_future --> return_booking
    two_week --> return_booking
    nextx --> return_booking
    range --> return_booking
    back2 --> return_booking
    return_booking --> home
end

%% ========= MAP PATHFINDER =========
subgraph MAP["Library Map Pathfinder"]
    map --> dest[[Choose Destination Symbol]]
    dest --> path_found{Path Found?}
    path_found -- Yes --> show_path[[Display Path]]
    path_found -- No --> show_error[[No Path Found]]
    show_path --> return_map[[Return to Main Menu]]
    show_error --> return_map
    return_map --> home
end

%% ========= LOGOUT =========
logout --> WELCOME
```