
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

%% ===== Library =====
    class Library {
        -name String
        -mediaAvailable List~MediaInterface~
        -map Map
        -resources List~Resource~
        +Library(name)
        +addMedia(media)
        +getMediaAvailable List~MediaInterface~
        +getResource(resourceName) Resource
        +addResource(resource)
        +showMedia(mediaID) MediaInterface
        +getName String
        +setName(name)
    }

    class Map {
        -map char[][]
        +Map(map)
        +getMap char[][]
    }

    Library *-- Map
    Library -- MediaInterface
    Library -- Resource


%% ===== Media =====
    class MediaInterface {
        <<interface>>
        +getMediaType String
        +getCreator String
        +getMediaGenre MediaGenres
        +borrowMedia(user)
        +returnMedia
        +getTitle String
        +getMediaID int
        +addCopies
        +addReview(review)
        +getReviews List~Review~
        +issueUser(user)
        +addWaitlist(user)
        +getWaitlist List~User~
        +removeFromWaitlist(user)
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
        -title String
        -author String
        -publisher String
        -mediaID int
        -library Library
        -genre MediaGenres
        -totalCopies int
        -issuedDays int
        -reviews List~Review~
        -waitlist List~User~
        +Book(title, author, publisher, genre, isbn, library)
        +borrowMedia(user)
        +returnMedia
        +getMediaType String
        +getCreator String
        +getMediaGenre MediaGenres
        +getTitle String
        +getAvailableCopies int
        +getLibrary Library
        +getMediaID int
        +addCopies
        +setLibrary(library)
        +addReview(review)
        +getReviews List~Review~
        +issueUser(user)
        +addWaitlist(user)
        +getWaitlist List~User~
        +removeFromWaitlist(user)
        +getIssuedDay int
        +setIssuedDay(num)
    }

    class Movie {
        -title String
        -director String
        -mediaID int
        -library Library
        -genre MediaGenres
        -issuedDays int
        -totalCopies int
        -reviews List~Review~
        -waitlist List~User~
        +Movie(title, director, mediaID, library, genre)
        +borrowMedia(user)
        +returnMedia
        +getMediaType String
        +getCreator String
        +getMediaGenre MediaGenres
        +getTitle String
        +getAvailableCopies int
        +getLibrary Library
        +getMediaID int
        +addCopies
        +addReview(review)
        +getReviews List~Review~
        +issueUser(user)
        +addWaitlist(user)
        +getWaitlist List~User~
        +removeFromWaitlist(user)
        +getIssuedDay int
        +setIssuedDay(num)
    }

    MediaInterface <|.. Book
    MediaInterface <|.. Movie


%% ===== Reviews =====
    class Review {
        +user User
        +media MediaInterface
        +comment String
        +stars int
    }

    User --> Review


%% ===== Users =====
    class User {
        -username String
        -password String
        -id int
        -email String
        -phone String
        -finesDue double
        -itemsIssued List~MediaInterface~
        +User(username, password, id, email, phone)
        +getID int
        +getUsername String
        +getEmail String
        +getPassword String
        +getItemsIssued List~MediaInterface~
        +issue(media)
        +calculateFinesDue double
        +checkBooksFines
        +checkMovieFines
        +checkBookFines(book)
        +checkMovieFines(movie)
        +clearFines
    }

    class UserManagement {
        -USERS List~User~
        +addUser(user) boolean
        +getUsers List~User~
        +userExistsBoolean(username) boolean
        +incrementID
        +getNextID int
    }

    UserManagement --> User


%% ===== Resources =====
    class Resource {
        <<interface>>
        +getResourceName String
        +getLibrary Library
        +markUnavailable(slot)
        +getUnavailableTimeSlots List~TimeSlots~
    }

    class StudyRoom {
        -roomName String
        -library Library
        -unavailableTimeSlots List~TimeSlots~
        +StudyRoom(roomName, library)
        +getResourceName String
        +getLibrary Library
        +markUnavailable(slot)
        +getUnavailableTimeSlots List~TimeSlots~
    }

    class Computer {
        -computerId String
        -library Library
        -unavailableTimeSlots List~TimeSlots~
        +Computer(id, library)
        +getResourceName String
        +getLibrary Library
        +markUnavailable(slot)
        +getUnavailableTimeSlots List~TimeSlots~
    }

    Resource <|.. StudyRoom
    Resource <|.. Computer


%% ===== Booking =====
    class Booking {
        -resource Resource
        -user User
        -timeSlot TimeSlots
        +Booking(resource, user, timeSlot)
        +getResource Resource
        +getUser User
        +getTimeSlot TimeSlots
    }

    class BookResource {
        -booking Booking
        -bookings List~BookResource~
        +BookResource(booking)
        +checkBooking
    }

    BookResource --> Booking


%% ===== Waitlist / Borrow =====
    class BorrowMedia {
        +BorrowMedia(user, media)
    }

    class Waitlist {
        +Waitlist(media, user)
    }

    BorrowMedia --> MediaInterface
    BorrowMedia --> User
    Waitlist --> MediaInterface
    Waitlist --> User


%% ===== TimeSlotSearch =====
    class TimeSlotSearch {
        -MAX_DAYS_AHEAD int
        +viewNextTwoWeeks(resource) List~String~
        +viewInRange(resource, start, end) List~String~
        +nextXAvailable(resource, afterTime, x) List~String~
    }

    class TimeSlots {
        <<enumeration>>
    }

    TimeSlotSearch --> Resource
    TimeSlotSearch --> TimeSlots


%% ===== Pathfinding =====
    class Coordinate {
        -x int
        -y int
        +Coordinate(x, y)
        +getX int
        +getY int
    }

    class LinkedListStack {
        -top Node
        -size int
        +push(item)
        +pop
        +size int
        +isEmpty boolean
        +peek
    }

    class EmptyStackException {
    }

    class PathFinder {
        -map char[][]
        +PathFinder(library)
        +runForTarget(targetChar)
        +clearPath
        +getMap char[][]
    }

    PathFinder --> Coordinate
    PathFinder --> LinkedListStack


%% ===== UI Layer =====
    class KioskUI {
        +showWelcomeScreen(library) User
        +showUserMenu(library, user) boolean
    }

    class LogInUI {
        +promptLogin(userManagement) User
    }

    class RegisterUserUI {
        +promptRegister(userManagement) User
    }

    KioskUI --> Library
    KioskUI --> User
    KioskUI --> BorrowMedia
    KioskUI --> Waitlist
    KioskUI --> BookResource
    KioskUI --> PathFinder
    KioskUI --> PrintMap
    KioskUI --> PrintMedia
    KioskUI --> PrintResource
    KioskUI --> TimeSlotSearch
    LogInUI --> UserManagement
    RegisterUserUI --> UserManagement





%% ===== Invariant properties  =====

    note for LogInUI "
Invariant properties:
- Username and password must not be null or blank when attempting login
"

    note for BookResource "
Invariant properties:
- booking != null
- No two bookings share the same resource + timeSlot
"

    note for Kiosk "
Invariant properties:
- library != null after initialization
- user is either null (logged out) or a valid User
"

    note for LibraryBuilder "
Invariant properties:
- Returned Library is never null
- All media and resources added are valid and non-null
"

    note for BorrowMedia "
Invariant properties:
- media != null
- user != null
"

    note for Coordinate "
Invariant properties:
- x >= 0
- y >= 0
- x and y never change after construction
"

    note for Library "
Invariant properties:
- name != null and name length > 0
- mediaAvailable != null
- resources != null
- map != null
- All media items stored are non-null
- All resources stored are non-null and have unique names
"

    note for Map "
Invariant properties:
- map != null
- map has at least one row and one column
"

    note for Book "
Invariant properties:
- title != null
- author != null
- publisher != null
- genre != null
- library != null
- totalCopies >= 0
- waitlist != null
- reviews != null
"

    note for Movie "
Invariant properties:
- title != null
- director != null
- genre != null
- library != null
- totalCopies >= 0
- waitlist != null
- reviews != null
"

    note for Review "
Invariant properties:
- user != null
- media != null
- comment != null
- 1 <= stars <= 10
"

    note for User "
Invariant properties:
- username != null and not empty
- password != null and not empty
- id > 0
- email != null and not empty
- phone != null and not empty
- finesDue >= 0
- itemsIssued != null
"

    note for UserManagement "
Invariant properties:
- USERS != null
- All usernames stored are unique
"

    note for StudyRoom "
Invariant properties:
- roomName != null and not empty
- library != null
- unavailableTimeSlots != null
"

    note for Computer "
Invariant properties:
- computerId != null and not empty
- library != null
- unavailableTimeSlots != null
"

    note for Booking "
Invariant properties:
- resource != null
- user != null
- timeSlot != null
"

    note for TimeSlots "
Invariant properties:
- each enum value has a non-null, non-empty label
"


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