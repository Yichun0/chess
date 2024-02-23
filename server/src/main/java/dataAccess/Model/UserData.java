package dataAccess.Model;

public class UserData {
    public String Username;
    public String Password;
    public String Email;

    public UserData(String Username, String Password, String Email){
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return Email;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
