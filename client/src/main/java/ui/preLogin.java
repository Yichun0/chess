package ui;

public class preLogin {
    public void help(){
        // display text informing user actions
        System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
        System.out.println("login <USERNAME> < PASSWORD> - to play chess");
        System.out.println("quit - playing chess");
        System.out.println("help = with possible commands");
    }
    public void quit(){
        // exit the program
        System.exit(0);
    }
    public void login(){
        //prompt the user to input log in information

        // call server login API
        // successful log in --> transition to PostLogin UI
    }
    public void register(){
        // input register information
        // call register API and Login API
        //successful --> transition to PostLogin
    }
}
