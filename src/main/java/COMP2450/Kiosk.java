package COMP2450;

import COMP2450.UI.KioskUI;
import COMP2450.domain.Library;
import COMP2450.domain.User;
import COMP2450.logic.LibraryBuilder;
import com.google.common.base.Preconditions;

/**
 * Kiosk
 * Acts as the main entry point and controller of the Library Management System.
 */

public class Kiosk {
    private static User user = null;
    private static Library library;

    public static void main(String[] args) {
        library = LibraryBuilder.initializeLibrary();
        Preconditions.checkNotNull(library);
        runKiosk();
        Preconditions.checkNotNull(library);
    }

    private static void runKiosk() {

        // To ensure if someone logs out we never exit the program
        while (true) {
            if (user == null) {
                user = KioskUI.showWelcomeScreen(library);
            } else {
                boolean stayInMenu = KioskUI.showUserMenu(library, user);
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
