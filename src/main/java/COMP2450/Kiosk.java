package COMP2450;

import COMP2450.UI.KioskUI;
import COMP2450.UI.LibraryLoader;
import COMP2450.domain.Library;
import COMP2450.domain.User;
import COMP2450.persistence.LibraryPersistence;
import COMP2450.persistence.UserPersistence;
import COMP2450.persistence.json.LibraryPersistenceJson;
import COMP2450.persistence.json.UserPersistenceJson;
import com.google.common.base.Preconditions;

import java.nio.file.Path;

/**
 * Kiosk
 * Acts as the main entry point and controller of the Library Management System.
 */



public class Kiosk {
    private static User user = null;
    private static Library library;

    public static void main(String[] args) {
        LibraryPersistence libraryPersistence = new LibraryPersistenceJson(Path.of("library.json"));
        UserPersistence userPersistence = new UserPersistenceJson(Path.of("users.json"));




        library = LibraryLoader.loadOrCreateLibrary();
        KioskUI.loadPersistence(userPersistence.loadUsers(library));
        Preconditions.checkNotNull(library);
        runKiosk(libraryPersistence , userPersistence);
        Preconditions.checkNotNull(library);
    }



    private static void runKiosk(LibraryPersistence libraryPersistence , UserPersistence userPersistence) {

        // To ensure if someone logs out we never exit the program
        while (true) {
            if (user == null) {
                user = KioskUI.showWelcomeScreen(library , libraryPersistence , userPersistence );
            } else {
                boolean stayInMenu = KioskUI.showUserMenu(library, user ,  libraryPersistence ,  userPersistence);
                if (!stayInMenu) {
                    logout();
                }
            }
        }
    }

    private static void logout() {
        if (user != null) {
            System.out.println("Logging out " + user.getUsername() + "...");
            user = null;
        }
    }
}
