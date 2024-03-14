package ui;

import java.security.Provider;
import java.util.Scanner;
public class PreLogin {

    private String serverUrl;
    private Scanner scanner;
    public PreLogin(Scanner scanner){
        this.scanner = new Scanner(System.in);
    }
    public void run(){
        System.out.println("Welcome to the chess game.");
        System.out.println("""
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> < PASSWORD> - to play chess
                quit - playing chess
                help - with possible commands
                """);
        String command = scanner.next();
        switch (command){
            case "help" -> help();
            case "register" -> register();
            case "login" -> login();
            case " quit" -> quit();
            default -> run();
        }

    }
    public void help(){
        // display text informing user actions
        System.out.println("Choose on of the options to start:");
        run();
    }
    public void quit(){
        // exit the program
        System.exit(0);
        System.out.println("Exit");
    }
    public void login(){
        //prompt the user to input log in information

        // call server login API
        // successful log in --> transition to PostLogin UI

    }
    public void register(){
        System.out.println("username: ");
        String username = scanner.next();
        System.out.println("password: ");
        String password = scanner.next();
        // input register information
        // call register API and Login API
        //successful --> transition to PostLogin
    }
}
