package bgu.spl.net.api;

public class User {
    private String userName;
    private String password;
    private boolean isActive;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.isActive=false;
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

    public boolean isActive(){return isActive;}
    public void changeState(boolean state){this.isActive=state;}


}
