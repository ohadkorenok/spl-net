package bgu.spl.net.api;

import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class User {
    private String userName;
    private String password;
    private boolean isActive = false;
    private LinkedList<User> following;
    private LinkedList<User> followers;


    public User(String userName, String password, boolean isActive) {
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.following = new LinkedList<>();
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


    public static LinkedList<String> userListToUserNameList(LinkedList<User> userList) {
        LinkedList<String> linkedListToReturn = new LinkedList<>();
        for (User user :
                userList) {
            linkedListToReturn.add(user.getUserName());
        }
        return linkedListToReturn;
    }

    public void addFollowing(User userToFollow) {
        following.add(userToFollow);
    }

    public void removeFollowing(User userToFollow) {
        following.remove(userToFollow);
    }

    public void addFollower(User userToFollow) {
        followers.add(userToFollow);
    }

    public void removeFollower(User userToFollow) {
        followers.remove(userToFollow);
    }

    public LinkedList<User> getFollowing() {
        return following;
    }

    public boolean isActive() {
        return isActive;
    }

    public LinkedList<User> getFollowers() {
        return followers;
    }
}
