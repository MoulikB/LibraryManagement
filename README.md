---
title: Library Project!
author: Moulik Bhatia (bhatiam3@myumanitoba.ca)
date: Fall 2025
---

# Library Project

## Resources

- I reasearched most of my project using different public library resources like :

1. Winnipeg library : <https://www.winnipeg.ca/recreation-leisure/libraries>
2. University Of Manitoba Library : <https://umanitoba.ca/libraries/>

## Diagram

The diagram for my domain model

```mermaid
classDiagram

class Media{
    - String : title
    - String : mediaType
    - String : author
    - String : publisher
    - String : genre
    - COMP2450.model.Shelf : location
    - int : copies
    - int : availableCopies
    - int : yearOfRelease
    - double : versionOrVolume
    - String : format
    + request(User user) void
    + leaveFeedback(Review review) void
}

class COMP2450.model.Shelf{
    - int : shelfNo
    + showOnMap() : Map
    }

class User {
    - String : userName
    - int : id
    - double : finesDue
    + borrowItem(Media): void
    + makeReview(Review): void
}

class Review{
    - int : reviewID
    - int : rating
    - String : comment
    + makeReview(): void
}

class Borrowing {
    - Date : borrowDate
    - Date : dueDate
    + borrow(Media, User): void
}

class Waitlist{
    - int : position
    + add(User): void
}

class BookingSpace {
    - int : spacesAvailable
    - int : spacesBooked
    - String : timeslots
    - String : type  // e.g. Room or Computer
    - Waitlist : waitTime
    + book(user: User): void
    + viewTimeSlots(): String
}

class Map{
     - String : legend
    - String : description
    + showMap(): Map
}

COMP2450.model.Shelf --> Media : contains
Media --> Borrowing : is borrowed via
User --> Review : makes
Borrowing --> Waitlist : waitlisted by
BookingSpace --> Waitlist : may have
User --> Waitlist : can join
Map --> COMP2450.model.Shelf : shows
Map --> BookingSpace : shows

```
