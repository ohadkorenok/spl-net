package bgu.spl.net.srv;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ServerToClientNullMessage;
import bgu.spl.net.api.User;

import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private ConcurrentHashMap<Integer, Message> messages = new ConcurrentHashMap<>();
    private static int messageId = 0;
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>(); // UserName, User.

    public boolean createUser(User user) {
        synchronized (users) {
            if (users.containsKey(user.getUserName())) {
                return false;
            } else {
                users.putIfAbsent(user.getUserName(), user);
                return true;
            }
        }
    }

    public void createMessage(Message message) {
        synchronized (messages) {
            messages.putIfAbsent(messageId, message);
            messageId++;
        }
    }

    public Message getMessage(int id) {
        return messages.getOrDefault(id, new ServerToClientNullMessage());
    }

    public User getUser(String userName) {
        return users.getOrDefault(userName, null);
    }

    //TODO:: finish update and delete.


}
