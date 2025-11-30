
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