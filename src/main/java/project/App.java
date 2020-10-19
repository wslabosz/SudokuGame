package project;

public class App {

    public static void main(final String[] args) {
        Greeter greeter = new Greeter();
        System.out.println(greeter.greet(args[0]));
    }
}