package COMP2450;

public class Main {
    public static void main(String[] args) {
        User moulik = new User("Moulik",123);
        User moulik2 = new User("Moulik",123);
        System.out.println((User.getStringOutput())); 
        System.out.println(moulik.userInfo());
        

    }
}