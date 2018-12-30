package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClient.AckMessage;
import bgu.spl.net.api.Messages.ServerToClient.ErrorMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.srv.Database;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;

public class UserListMessage extends ClientToServerMessage {
    private int NUMBEROFARGS = 1;
    private static final short opCode = 7;
    private final State state = State.USERLIST;

    @Override
    public void decode(LinkedList<byte[]> args) {

        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- USERLIST. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in USERLIST -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
        }

    }

    @Override
    public ServerToClientMessage process(Database db, Connections connection, int connectionId) {
        User user = fetchActiveUser(db, connectionId);
        if (user != null) {
            LinkedList<String> userString = new LinkedList<>();
            Collection<User> userEnumeration = db.getAllUsers();
            Arrays.sort(userEnumeration.toArray());
            for (User useri :
                    userEnumeration) {
                userString.add(useri.getUserName());
            }
            return new AckMessage(opCode, userString,(short)userString.size());
        } else {
            return new ErrorMessage(opCode);
        }
    }
}
