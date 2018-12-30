package bgu.spl.net.srv;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ServerToClientNullMessage;
import bgu.spl.net.api.User;

import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private ConcurrentHashMap<User, LinkedList<Message>> messages = new ConcurrentHashMap<>();
    private static int messageId = 0;
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>(); // UserName, User.
    private ConcurrentHashMap<Integer, User> activeUsers = new ConcurrentHashMap<>(); // UserName, User.
    private LinkedList<String> userNamesByRegistration = new LinkedList<>();


    public boolean createUser(User user) {
        synchronized (users) {
            if (users.containsKey(user.getUserName())) {
                return false;
            } else {
                if (users.putIfAbsent(user.getUserName(), user) == null) {
                    synchronized (userNamesByRegistration) {
                        userNamesByRegistration.add(user.getUserName());
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
    }


    public boolean createActiveUser(User user, int connectionId) {
        synchronized (users) {
            if (activeUsers.containsKey(connectionId)) {
                return false;
            } else {
                activeUsers.putIfAbsent(connectionId, user);
                return true;
            }
        }
    }

    /**
     * This method pushes the message into the desired message linked list of the user.
     *
     * @param user    User
     * @param message Message
     */
    public void createMessage(User user, Message message) {
        synchronized (messages) {
            if (messages.containsKey(user)) {
                LinkedList<Message> messageList = messages.get(user);
                messageList.add(message);
            } else {
                LinkedList<Message> toPush = new LinkedList<>();
                toPush.add(message);
                messages.put(user, toPush);
            }
            messageId++;
        }
    }

    public User checkActiveUser(int connId) {
        if (activeUsers.containsKey(connId))
            return activeUsers.get(connId);
        return null;
    }


    public User getUser(String userName) {
        return users.getOrDefault(userName, null);
    }

    public void removeActiveUser(int connectionId) {
        activeUsers.remove(connectionId);
    }

    public LinkedList<String> getAllUsers() {
        return userNamesByRegistration;
    }

    public LinkedList<Message> getMessagesOfUser(User user) {
        return messages.getOrDefault(user, new LinkedList<>());
    }

    //TODO:: finish update and delete.


}
