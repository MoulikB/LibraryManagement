import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainTester {

    public static void main(String[] args) {
        // Prepare input sequence with a valid flow
        StringBuilder sb = new StringBuilder();

        // 1. ADD MEMBER
        sb.append("1\n");                 // menu option
        sb.append("Alice\n");             // name
        sb.append("1\n");                 // id
        sb.append("alice@example.com\n"); // email
        sb.append("1234567890\n");        // phone

        // 2. SHOW MEMBER
        sb.append("3\n");
        sb.append("1\n");                 // member ID

        // 3. ADD LIBRARY
        sb.append("4\n");
        sb.append("CentralLibrary\n");

        // 4. SHOW LIBRARY
        sb.append("5\n");
        sb.append("CentralLibrary\n");

        // 5. ADD MEDIA (Book)
        sb.append("7\n");
        sb.append("book\n");              // type
        sb.append("Effective Java\n");    // title
        sb.append("Joshua Bloch\n");      // author
        sb.append("Pearson\n");           // publisher
        sb.append("FICTION\n");           // genre (must exist in MediaGenres enum)
        sb.append("101\n");               // mediaID
        sb.append("CentralLibrary\n");    // library

        // 6. SHOW MEDIA
        sb.append("9\n");
        sb.append("CentralLibrary\n");    // library
        sb.append("101\n");               // mediaID

        // 7. ADD RESOURCE (Computer)
        sb.append("10\n");
        sb.append("CentralLibrary\n");    // library
        sb.append("computer\n");          // type
        sb.append("C1\n");                // computer ID

        // 8. SHOW RESOURCE
        sb.append("11\n");
        sb.append("CentralLibrary\n");    // library
        sb.append("C1\n");                // resource name

        // 9. ADD REVIEW
        sb.append("12\n");
        sb.append("1\n");                 // user ID
        sb.append("CentralLibrary\n");    // library
        sb.append("101\n");               // mediaID
        sb.append("Great book!\n");       // comment
        sb.append("9\n");                 // stars

        // 10. SHOW REVIEWS
        sb.append("13\n");
        sb.append("1\n");                 // user ID
        sb.append("CentralLibrary\n");    // library
        sb.append("101\n");               // mediaID

        // 11. SHOW MAP
        sb.append("14\n");
        sb.append("CentralLibrary\n");

        // 12. REMOVE MEDIA
        sb.append("8\n");
        sb.append("101\n");               // mediaID
        sb.append("CentralLibrary\n");

        // 13. REMOVE MEMBER
        sb.append("2\n");
        sb.append("1\n");                 // member ID

        // 14. REMOVE LIBRARY
        sb.append("6\n");
        sb.append("CentralLibrary\n");

        // 15. RESET
        sb.append("15\n");

        // 16. EXIT
        sb.append("16\n");

        // Feed this scripted input into Main
        InputStream input = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(input);

        Main.main(new String[]{});
    }
}
