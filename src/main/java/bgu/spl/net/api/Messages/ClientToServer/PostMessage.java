package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClient.AckMessage;
import bgu.spl.net.api.Messages.ServerToClient.ErrorMessage;
import bgu.spl.net.api.Messages.ServerToClient.NotificationMessage;
import bgu.spl.net.api.Messages.ServerToClient.ServerToClientNullMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.srv.Database;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class PostMessage extends ClientToServerMessage {
    private static final int NUMBEROFARGS = 2;
    private static final short opCode = 5;
    private String content;
    private static final short notificationType = 1;


    private final State state = State.POST;


    @Override
    public void decode(LinkedList<byte[]> args) {
        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- POST. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in POST -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
            content = new String(args.get(1));
        }
    }

    @Override
    public ServerToClientMessage process(Database db, Connections connections, int connectionId) {
        ServerToClientMessage serverToClientMessage;

        User user = fetchActiveUser(db, connectionId);
        if (user == null) {
            serverToClientMessage = new ErrorMessage(opCode);
        } else {
            LinkedList<String> usersFromContent = extractUsersFromContent();
            Set<User> recipients = stringUserNamesToUserSet(usersFromContent, db);
            recipients.addAll(user.getFollowers());
            if(!recipients.isEmpty()) {
                ServerToClientMessage message = NotificationMessage.handleNotificationToRecipients(notificationType, user, recipients, content, connections);
                db.createMessage(user, message);
            }
            serverToClientMessage = new AckMessage(opCode,new LinkedList<>());
        }

        return serverToClientMessage;
    }


    private LinkedList<String> extractUsersFromContent() {
        LinkedList<String> usersFromContent = new LinkedList<>();
        String userName = "";
        boolean listenChars = false;
        for (char ch :
                content.toCharArray()) {
            if (ch == '@') {
                listenChars = true;
            }
            if (ch == ' ' && !userName.isEmpty() && listenChars) {
                usersFromContent.add(userName);
                listenChars = false;
            }
            if (listenChars) {
                userName += ch;
            }
        }

        if (!userName.isEmpty() && listenChars) {
            usersFromContent.add(userName);
        }

        return usersFromContent;
    }


    /**
     * This method adds the tagged users to the recipients set.
     *
     * @param userNames LinkedList<String></String>
     * @param db        Database
     * @return Set<User> of tagged users.
     */
    public static Set<User> stringUserNamesToUserSet(LinkedList<String> userNames, Database db) {
        Set<User> recipients = new HashSet<>();
        for (String userString :
                userNames) {
            User userFromUserString = db.getUser(userString);
            if (userFromUserString != null) {
                recipients.add(userFromUserString);
            }
        }
        return recipients;
    }
}
