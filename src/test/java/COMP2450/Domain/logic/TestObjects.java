package COMP2450.Domain.logic;

import COMP2450.domain.*;
import COMP2450.domain.Media.Book;
import COMP2450.domain.Resources.StudyRoom;

public class TestObjects {

    public static User sampleUser() {
        return new User("tester", "pass123",  1 ,"tester@example.com" ,"1234567890");
    }

    public static Book sampleBook(Library lib) {
        return new Book("Test Book", "Author", "Publisher",
                MediaGenres.FICTION, 111, lib);
    }

    public static StudyRoom sampleRoom(Library lib) {
        return new StudyRoom("Room A", lib);
    }
}
