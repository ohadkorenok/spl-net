package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.AckMessage;
import bgu.spl.net.api.Messages.ServerToClient.ErrorMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class StatMessage extends ClientToServerMessage {
    private int NUMBEROFARGS = 2;
    private static final short opCode = 8;
    private String userName;
    private final State state = State.STAT;


    @Override
    public void decode(LinkedList<byte[]> args) {
        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- STAT . got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in STAT -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
            userName = new String(args.get(1));
        }
    }

    /**
     * This method counts the posts of the user , counts the number of followers , the number of following and creates a
     * new ACK message contains the desired data.
     *
     * @param db           Database
     * @param connection   Connections
     * @param connectionId int
     * @return AckMessage/ ErrorMessage.
     */
    @Override
    public ServerToClientMessage process(Database db, Connections connection, int connectionId) {
        User user = fetchActiveUser(db, connectionId);
        int postsCounter = 0;
        LinkedList<Short> args = new LinkedList<>();
        if (user != null) {
            LinkedList<Message> messages = db.getMessagesOfUser(user);
            for (Message message :
                    messages) {
                if (message instanceof PostMessage) {
                    postsCounter++;
                }
            }
            args.addFirst((short)postsCounter);
            args.add((short)user.getFollowers().size());
            args.add((short)user.getFollowing().size());

            return new AckMessage(opCode, new LinkedList<>(),args);
        } else {
            return new ErrorMessage(opCode);
        }
    }
}
