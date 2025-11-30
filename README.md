
---
title: Library Management System
author: Moulik Bhatia (bhatiam3@myumanitoba.ca)
date: 30 September 2025
---

# Domain model
This repository contains a domain model and an initial implementation of our Library System.
It maintains a simple library and user database management and manages users, media (books and movies) along with resources (computers and study rooms) and a booking system for said resources.
Phase 2:
Added a kiosk that allows user to perform basic functions and find they way to different resources
To run this use the Kiosk.java

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
        -issuedDays: int
        -reviews: List<Review>
        -currentlyIssuedTo: List<User>
        -waitlist: List<User>
        +Book(title: String, author: String, publisher: String, genre: MediaGenres, isbn: int, library: Library)
        +checkInvariants() void
        +getMediaType() String
        +getCreator() String
        +getMediaGenre() MediaGenres
        +borrowMedia(user: User) void
        +returnMedia() void
        +getTitle() String
        +getAvailableCopies() int
        +getLibrary() Library
        +getMediaID() int
        +addCopies() void
        +setLibrary(library: Library) void
        +mediaExists(media: MediaInterface) boolean
        +addReview(review: Review) void
        +getReviews() List<Review>
        +issueUser(user: User) boolean
        +addWaitlist(user: User) void
        +getWaitlist() List<User>
        +removeFromWaitlist(user: User) void
    }

    class Movie {
        -title: String
        -director: String
        -mediaID: int
        -library: Library
        -genre: MediaGenres
        -issuedDays: int
        -totalCopies: int
        -reviews: List<Review>
        -currentlyIssuedTo: List<User>
        -waitlist: List<User>
        +Movie(title: String, director: String, mediaID: int, library: Library, genre: MediaGenres)
        +checkInvariants() void
        +getMediaType() String
        +getCreator() String
        +getMediaGenre() MediaGenres
        +borrowMedia(user: User) void
        +returnMedia() void
        +getTitle() String
        +getAvailableCopies() int
        +getLibrary() Library
        +getMediaID() int
        +addCopies() void
        +mediaExists(media: MediaInterface) boolean
        +addReview(review: Review) void
        +getReviews() List<Review>
        +issueUser(user: User) boolean
        +addWaitlist(user: User) void
        +getWaitlist() List<User>
        +removeFromWaitlist(user: User) void
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
        - username: String
        - password: String
        - id: int
        - email: String
        - phone: String
        - finesDue: double
        - reviewsWritten: List<Review>
        - itemsIssued: List<MediaInterface>
        + User(username: String, password: String, id: int, email: String, phone: String)
        + checkInvariants(): void
        + getID(): int
        + getUsername(): String
        + getEmail(): String
        + getPhone(): String
        + addReview(review: Review): void
        + getReviews(): List<Review>
        + equals(otherUser: User): boolean
        + getItemsIssued(): List<MediaInterface>
        + issue(media: MediaInterface): void
        + calculateFinesDue(): double
        + checkBooksFines(): void
        + checkMovieFines(): void
        + checkBookFines(book: Book): void
        + checkMovieFines(movie: Movie): void
        + getPassword(): String
        + clearFines(): void
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

%% ===== User Interface (KioskUI) Layer =====
    class KioskUI {
        - library: Library
        - user: User
        + showWelcomeScreen(library: Library): User
        + showUserMenu(library: Library, user: User): boolean
        - incrementIssuedDaysForAllUsers(): void
        - browseMedia(library: Library): void
        - borrowMedia(library: Library, user: User): void
        - returnMedia(library: Library, user: User): void
        - viewResources(library: Library): void
        - bookResource(library: Library, user: User): void
        - handleBooking(resource: Resource, user: User, date: LocalDate): void
        - handleFutureBooking(resource: Resource, user: User): void
        - showNextXAfterTime(resource: Resource): void
        - showRangeAvailability(resource: Resource): void
        - findPathOnMap(library: Library): void
        - findMediaOnMap(library: Library): void
        - checkFines(user: User): void
        - payFine(user: User): void
        - clearFines(user: User): void
        - promptMenu(options: String[]): int
    }

    class LogInUI {
        + promptLogin(): User
    }

    class RegisterUserUI {
        + promptRegister(): User
    }

    KioskUI --> Library : uses
    KioskUI --> User : activeUser
    KioskUI --> BorrowMedia : uses
    KioskUI --> Waitlist : uses
    KioskUI --> BookResource : uses
    KioskUI --> LogInUI : uses
    KioskUI --> RegisterUserUI : uses
    KioskUI --> Resource : books
    KioskUI --> PathFinder : navigates
    KioskUI --> PrintMap : prints
    KioskUI --> PrintMedia : displays
    KioskUI --> PrintResource : lists
    KioskUI --> TimeSlotSearch : checks
    LogInUI --> UserManagement : validates
    RegisterUserUI --> UserManagement : registers

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



    class Kiosk {
        - user : User
        - library : Library
        + main(args : String[]) void
        - runKiosk() void
        - logout() void
    }

    Kiosk --> User
    Kiosk --> Library
    Kiosk --> UI
    Kiosk --> LibraryBuilder

    class RegisterUserUI {
        + promptRegister() User
    }

    RegisterUserUI --> RegisterUser
    RegisterUserUI --> InputValidation
    RegisterUserUI --> User
    class LogInUI {
        + promptLogin() User
    }

    LogInUI --> LogIn
    LogInUI --> InputValidation
    LogInUI --> User

    class TimeSlotSearch {
        - MAX_DAYS_AHEAD : int
        + viewNextTwoWeeks(resource : Resource) List<String>
        + viewInRange(resource : Resource, start : LocalDate, end : LocalDate) List<String>
        + nextXAvailable(resource : Resource, afterTime : LocalTime, x : int) List<String>
        - parseStartTime(slot : TimeSlots) LocalTime
    }

    TimeSlotSearch --> Resource
    TimeSlotSearch --> TimeSlots

    class LibraryBuilder {
        + initializeLibrary() Library
        - addMedia(library : Library) void
        - addResources(library : Library) void
    }

    LibraryBuilder --> Library
    LibraryBuilder --> Book
    LibraryBuilder --> Movie
    LibraryBuilder --> StudyRoom
    LibraryBuilder --> Computer
    LibraryBuilder --> MediaGenres

    class BookResource {
        + booking : Booking
        + bookings : ArrayList<BookResource>
        + BookResource(booking : Booking)
        + checkBooking() void
    }
    BookResource --> Booking
    BookResource --> BookingConflictException

    class BorrowMedia {
        + issueUser(media : MediaInterface, user : User) void
    }

    BorrowMedia --> MediaInterface
    BorrowMedia --> User
    BorrowMedia --> UnavailableMediaException
    BorrowMedia --> OverdueMediaException

    class Waitlist {
        + waitlistUser(media : MediaInterface, user : User) void
    }

    Waitlist --> MediaInterface
    Waitlist --> User
    class Coordinate {
        - x : int
        - y : int
        + Coordinate(x : int, y : int)
        + getX() int
        + getY() int
        + equals(other : Coordinate) boolean
    }




%% ===== Invariant properties  =====
    note for LogInUI "Invariant properties:\n- Username and password must not be blank or null "
    note for BookResource "Invariant properties:\n- booking != null for all instances.\n- No two bookings share the same resource/time slot."
    note for Kiosk "Invariant properties:\n- library != null after initialization\n- user == null or valid User\n"
    note for LibraryBuilder "Invariant properties:\n- Returned Library is never null.\n- All media/resources added are valid.\n"
    note for BorrowMedia "Invariant properties:\n- media != null and user != null."
    note for Coordinate "Invariant properties:\n- x, y immutable after construction.\n- x >= 0 and y >= 0"
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

The following diagram shows the flow for the `Kiosk` interface
This flow illustrates how users navigate between menus —

```mermaid
%% ========== 1. WELCOME / LOGIN / REGISTER ==========
flowchart TD

    subgraph WELCOME["WELCOME / LOGIN / REGISTER"]
        start([Start Kiosk])
        login[Log In]
        register[Register New User]
        login_result{Valid credentials?}
        register_result{Registration successful?}
        simulate[Simulate +1 Day]
        home[Go to Main Menu]
        exit([Exit Program])
        login_error[Show 'Invalid Credentials']
        register_error[Show 'Registration Failed']

        start --> login
        start --> register
        start --> exit

        login --> login_result
        login_result -- Yes --> simulate --> home
        login_result -- No --> login_error --> login

        register --> register_result
        register_result -- Yes --> simulate --> home
        register_result -- No --> register_error --> register
    end



%% ========== 2. MAIN MENU ==========

    subgraph MAIN_MENU["MAIN MENU"]
        home2[Main Menu]

        home2 --> browse[Browse Media]
        home2 --> borrow[Borrow Media]
        home2 --> returnm[Return Media]
        home2 --> viewres[View Resources]
        home2 --> bookres[Book a Resource]
        home2 --> map[Find Path on Map]
        home2 --> media_map[Find Media on Map]
        home2 --> check_fine[Check Fines]
        home2 --> pay_fine[Pay Fines]
        home2 --> logout[Log Out]
    end



%% ========== 3. BROWSE MEDIA ==========


    subgraph BROWSE_MEDIA["BROWSE MEDIA"]
        browse2[Browse Media]

        browse2 --> all[Browse All Media]
        browse2 --> movies[Browse All Movies]
        browse2 --> books[Browse All Books]
        browse2 --> director[View by Director]
        browse2 --> author[View by Author]

        browse2 --> title_search[Search by Title]
        title_search --> title_found{Title found?}
        title_found -- No --> title_error[Show 'Title Not Found'] --> browse2
        title_found -- Yes --> title_show[Display Matching Media] --> browse2
    end



%% ========== 4. BORROW MEDIA ==========


    subgraph BORROW_MEDIA["BORROW MEDIA"]
        borrow2[Borrow Media]

        borrow2 --> find_media[Enter Media ID]
        find_media --> id_valid{Media ID valid?}

        id_valid -- No --> invalid_id[Show 'Invalid Media ID'] --> borrow2

        id_valid -- Yes --> available{Media available?}

        available -- Yes --> borrow_success[Borrow Successful]

        available -- No --> waitlist_choice{Join waitlist?}
        waitlist_choice -- Yes --> waitlist_added[Added to Waitlist]
        waitlist_choice -- No --> waitlist_declined[Not Added]
    end



%% ========== 5. RETURN MEDIA ==========

    subgraph RETURN_MEDIA["RETURN MEDIA"]
        returnm2[Return Media]

        returnm2 --> show_borrowed[Show Borrowed Media]
        show_borrowed --> enter_id[Enter Media ID]
        enter_id --> valid_return{Return valid?}

        valid_return -- No --> invalid_return[Show 'Invalid Return'] --> returnm2

        valid_return -- Yes --> confirm_return[Return Confirmed]

        confirm_return --> review_prompt{Leave a review?}
        review_prompt -- Yes --> review_yes[Submit Review]
        review_prompt -- No --> skip_review[Skip Review]
    end



%% ========== 6. RESOURCE BOOKING ==========


    subgraph BOOKING["RESOURCE BOOKING"]
        bookres2[Book Resource]

        bookres2 --> resource_name[Enter Resource Name]
        resource_name --> exists{Resource exists?}

        exists -- No --> no_res[Show 'Resource Not Found']

        exists -- Yes --> resource_menu[Show Booking Options]

        resource_menu --> today[Book Today]
        resource_menu --> future[Book Future Date]
        resource_menu --> two_week[View 2 Weeks Availability]
        resource_menu --> nextx[Next X Available]
        resource_menu --> range[View in Date Range]
        resource_menu --> back2[Back]

        today --> conflict_today{Conflict?}
        conflict_today -- Yes --> booked_today_err[Slot Already Booked]
        conflict_today -- No --> confirm_today[Booking Confirmed]

        future --> conflict_future{Conflict?}
        conflict_future -- Yes --> booked_future_err[Slot Already Booked]
        conflict_future -- No --> confirm_future[Booking Confirmed]
    end



%% ========== 7. MAP PATHFINDER ==========
  

    subgraph MAP["MAP PATHFINDER"]
        map2[Find Path]

        map2 --> dest[Choose Destination Symbol]
        dest --> valid_dest{Symbol valid?}

        valid_dest -- No --> invalid_dest[Invalid Symbol] --> map2

        valid_dest -- Yes --> path_found{Path found?}
        path_found -- Yes --> show_path[Show Path]
        path_found -- No --> show_error[No Path Found]
    end



%% ========== 8. FIND MEDIA ON MAP ==========


    subgraph MEDIA_MAP["FIND MEDIA ON MAP"]
        media_map2[Find Media on Map]

        media_map2 --> title_search2[Search by Title]
        title_search2 --> found{Media found?}

        found -- No --> not_found[Show 'Media Not Found']

        found -- Yes --> section[Show Section Symbol + Genre]

        section --> path_choice{Find path on map?}
        path_choice -- Yes --> show_media_path[Show Path]
        path_choice -- No --> no_path[Do Not Show Path]
    end



%% ========== 9. FINES SYSTEM ==========

    
    subgraph FINES["FINES SYSTEM"]
        check_fine2[Check Fines]

        check_fine2 --> check_issue{Any issued media?}
        check_issue -- No --> no_issue[Show 'No Media Issued']
        check_issue -- Yes --> calc_fine[Calculate Fines]

        calc_fine --> fine_amount{Any fines?}
        fine_amount -- No --> no_fine[Show 'No Fines']
        fine_amount -- Yes --> due_display[Show Fine Amount]

        pay_fine2[Pay Fines] --> pay_prompt[Enter Payment Info]
        pay_prompt --> payment_valid{Payment info valid?}

        payment_valid -- No --> payment_invalid[Invalid Payment Info]

        payment_valid -- Yes --> pay_result{Payment successful?}
        pay_result -- Yes --> return_overdue[Return Overdue Media] --> fines_cleared[Clear All Fines]
        pay_result -- No --> cancel_pay[Payment Cancelled]
    end



```