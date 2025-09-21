package COMP2450.model;

public class Book implements MediaInterface {
    private String title;
    private String author;
    private String publisher;
    private int mediaID;
    private Library library;
    private MediaGenres genre;
    int totalCopies = 0;


    public Book(String title, String author, String publisher,
                MediaGenres genre, int isbn, Library library) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.mediaID = isbn;
        this.library = library;
        addToLibrary(this);
    }




        public String getMediaType () {
            return "Book";
        }

        public String getCreator () {
            return author;
        }

        public MediaGenres getMediaGenre () {
            return genre;
        }

        public boolean borrowMedia () {
            if (this.totalCopies > 0) {
                this.totalCopies--;
                return true;
            } else
                return false;
        }


        public void returnMedia () {
            totalCopies++;
        }

        public String getTitle () {
            return title;
        }

        public int getAvailableCopies () {
            return totalCopies;
        }

        public String getPublisher () {
            return publisher;
        }

        public Library getLibrary () {
            return library;
        }

        public int getMediaID () {
            return mediaID;
        }

        public void addCopies() {
            totalCopies++;
        }
}

