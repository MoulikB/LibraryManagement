package COMP2450;

import COMP2450.UI.UI;
import COMP2450.domain.Library;
import COMP2450.domain.User;
import COMP2450.logic.LibraryBuilder;

public class Kiosk {
    private static User user = null;
    private static Library library;

    public static void main(String[] args) {
        library = LibraryBuilder.initializeLibrary();
        runKiosk();
    }

    private static void runKiosk() {
        boolean running = true;

        while (running) {
            if (user == null) {
                user = UI.showWelcomeScreen(library);
            } else {
                boolean stayInMenu = UI.showUserMenu(library, user);
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
