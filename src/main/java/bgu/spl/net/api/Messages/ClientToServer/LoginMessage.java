package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClient.AckMessage;
import bgu.spl.net.api.Messages.ServerToClient.ErrorMessage;
import bgu.spl.net.api.Messages.ServerToClient.NotificationMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.srv.Database;

import java.util.Collections;
import java.util.LinkedList;

public class LoginMessage extends ClientToServerMessage {
    @Override
    public String toString() {
        return "LOGIN " + userName + " " + password;
    }
    @Override
    public ServerToClientMessage process(Database db, Connections connections, int connectionId) {
        User user = validateUser(db);
        if (user == null) {
            return new ErrorMessage(opCode);
        }
        ServerToClientMessage messageToRecieve = db.createActiveUser(user, connectionId) ? new AckMessage(opCode, new LinkedList<>()) : new ErrorMessage(opCode);
        if (!(messageToRecieve instanceof ErrorMessage)) {
            user.setActiveConnectionId(connectionId);
            user.setIsActive(true);
            LinkedList<NotificationMessage> notificationMessages = user.getNotifications();
            Collections.sort(notificationMessages);
            for (NotificationMessage notificationMessage :
                    notificationMessages) {
                if (!connections.send(connectionId, notificationMessage)) {
                    System.out.println("connections.send() == false in LOGIN, for handling notifications.");
                }
            }
            notificationMessages.clear();

        }
        return messageToRecieve;
    }

    private static final int NUMBEROFARGS = 3;
    private static final short opCode = 2;
    private String userName;
    private String password;


    @Override
    public void decode(LinkedList<byte[]> args) {
        this.state = State.LOGIN;
        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- login. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in Login -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
            userName = new String(args.get(1));
            password = new String(args.get(2));
        }
    }


    private User validateUser(Database db) {
        User user = db.getUser(userName);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

}

