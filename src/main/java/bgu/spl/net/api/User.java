package bgu.spl.net.api;

import bgu.spl.net.api.Messages.ServerToClient.NotificationMessage;
import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class User implements Comparable{
    private String userName;
    private String password;
    private volatile boolean isActive = false;
    private LinkedList<User> following;
    private LinkedList<User> followers;
    private LinkedList<NotificationMessage> notifications;
    private int activeConnectionId;
    private static int totalIds = 0;
    private int userId;


    public User(String userName, String password, boolean isActive) {
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.following = new LinkedList<>();
        this.followers = new LinkedList<>();
        this.notifications = new LinkedList<>();
        this.userId = totalIds;
        totalIds++;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName; }

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

    public boolean addFollowing(User userToFollow) {
        synchronized (following) {
            if(!following.contains(userToFollow)) {
                following.add(userToFollow);
                return true;
            }
            return false;
        }
    }

    public boolean removeFollowing(User userToFollow) {
        synchronized (following) {
               return following.remove(userToFollow);
        }
    }

    public void addFollower(User userToFollow) {
        synchronized (followers) {
            followers.add(userToFollow);
        }
    }

    public void removeFollower(User userToFollow) {
        synchronized (followers) {
            followers.remove(userToFollow);
        }
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

    public LinkedList<NotificationMessage> getNotifications() {
        return notifications;
    }

    public void addNotification(NotificationMessage notification) {
        synchronized(notification) {
            this.notifications.add(notification);
        }
    }

    public int getActiveConnectionId() {
        return activeConnectionId;
    }

    public void setActiveConnectionId(int activeConnectionId) {
        this.activeConnectionId = activeConnectionId;
    }

    public void setIsActive(boolean answer) {
        isActive = answer;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof User)){
            throw new IllegalArgumentException("Object not from user instance");
        }
        else{
            return Integer.compare(userId, ((User) o).getUserId());
        }
    }

    public int getUserId() {
        return userId;
    }
}
