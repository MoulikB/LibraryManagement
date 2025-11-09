
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
StudyRoom "1" <-- "0..*" Booking : maintains
Computer "1" <-- "0..*" Booking : maintains
StudyRoom "1" <-- "1" Library : located at
Computer "1" <-- "1" Library : located at

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
 ``` mermaid
flowchart TD

%% ========= SIGN IN / REGISTER =========
subgraph SIGN_IN[**SIGN IN / REGISTER**]
login[[Log In]]
register[[Register New User]]
login_result{Valid credentials?}
register_result{Registration successful?}
exit[[Exit Application]]
home[[Main Menu]]

    login --> login_result
    login_result -- valid --> home
    login_result -- invalid --> login

    register --> register_result
    register_result -- success --> home
    register_result -- failed --> register

    login -.or register-.-> exit
end


%% ========= MAIN MENU =========
subgraph MAIN_MENU[**USER MAIN MENU**]
home[[Main Menu]]
browse_media[[Browse Media]]
borrow_media[[Borrow Media]]
return_media[[Return Media]]
view_resources[[View Resources]]
book_resource[[Book Resource]]
map_pathfinder[[Find Path on Map]]
logout[[Log Out]]

    home --> browse_media
    home --> borrow_media
    home --> return_media
    home --> view_resources
    home --> book_resource
    home --> map_pathfinder
    home --> logout
end


%% ========= BROWSE MEDIA =========
subgraph BROWSE_MEDIA[**BROWSE MEDIA**]
start[[Browse Menu]]
choice{Select an option}
all_media[[Show all media]]
all_books[[Show all books]]
all_movies[[Show all movies]]
by_author[[View books by author]]
by_director[[View movies by director]]
by_title[[Search by title]]
back[[Go Back to Main Menu]]

    start --> choice
    choice --> all_media
    choice --> all_books
    choice --> all_movies
    choice --> by_author
    choice --> by_director
    choice --> by_title
    choice --> back
end


%% ========= BORROW MEDIA =========
subgraph BORROW_MEDIA[**BORROW MEDIA**]
start_borrow[[Borrow Menu]]
enter_id[[Enter Media ID]]
waitlist_check{Waitlist has users?}
join_waitlist_from_full[[Ask to join waitlist]]
availability{Copies available?}
issue_media[[Issue media to user]]
unavailable[[No copies available]]
waitlist_choice{Join waitlist?}
add_waitlist[[Add to waitlist]]
cancel_borrow[[Cancel borrow]]
borrow_done[[Show confirmation]]
back_borrow[[Go Back]]

    start_borrow --> enter_id --> waitlist_check
    waitlist_check -- yes --> join_waitlist_from_full --> waitlist_choice
    waitlist_check -- no --> availability
    availability -- yes --> issue_media --> borrow_done
    availability -- no --> unavailable --> waitlist_choice
    waitlist_choice -- yes --> add_waitlist --> borrow_done
    waitlist_choice -- no --> cancel_borrow
    borrow_done --> back_borrow
end


%% ========= RETURN MEDIA =========
subgraph RETURN_MEDIA[**RETURN MEDIA**]
start_return[[Return Menu]]
enter_return_id[[Enter Media ID]]
return_item[[Return media]]
confirm_return[[Show confirmation]]
back_return[[Go Back]]

    start_return --> enter_return_id --> return_item --> confirm_return --> back_return
end


%% ========= VIEW RESOURCES =========
subgraph VIEW_RESOURCES[**VIEW RESOURCES**]
start_view[[View Resources Menu]]
display_resources[[Display all available resources]]
back_view[[Go Back]]

    start_view --> display_resources --> back_view
end


%% ========= BOOK RESOURCE =========
subgraph BOOK_RESOURCE[**BOOK RESOURCE**]
start_book[[Book Resource Menu]]
input_resource[[Enter Resource Name]]
resource_found{Resource found?}
invalid_resource[[Show error message]]
choose_slot[[Display available time slots]]
confirm_booking[[Confirm booking]]
booking_status{Booking successful?}
success_booking[[Show success message]]
conflict_booking[[Show conflict message]]
back_book[[Go Back]]

    start_book --> input_resource --> resource_found
    resource_found -- no --> invalid_resource --> back_book
    resource_found -- yes --> choose_slot --> confirm_booking --> booking_status
    booking_status -- yes --> success_booking --> back_book
    booking_status -- no --> conflict_booking --> back_book
end


%% ========= MAP PATH FINDER =========
subgraph MAP_PATHFINDER[**MAP PATH FINDER**]
start_map[[Map Menu]]
show_legend[[Display map legend]]
input_symbol[[Enter destination symbol]]
valid_symbol{Valid input?}
find_path[[Run pathfinding algorithm]]
result{Path found?}
show_path[[Display path on map]]
no_path[[Display: No path found]]
back_map[[Go Back]]

    start_map --> show_legend --> input_symbol --> valid_symbol
    valid_symbol -- no --> back_map
    valid_symbol -- yes --> find_path --> result
    result -- yes --> show_path --> back_map
    result -- no --> no_path --> back_map
end


%% ========= LOG OUT =========
subgraph LOGOUT[**LOG OUT**]
logout_action[[Log Out]]
clear_session[[Clear current user data]]
return_signin[[Return to Sign-In screen]]

    logout_action --> clear_session --> return_signin
end





```

