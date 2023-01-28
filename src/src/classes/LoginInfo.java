package src.classes;

public class LoginInfo {
    private String userName;
    private String password;

    LoginInfo(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }
}
