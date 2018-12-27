package bgu.spl.net.api;

public class User {
    private String userName;
    private String password;
    private boolean isActive = false;

    public User(String userName, String password, boolean isActive){
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
