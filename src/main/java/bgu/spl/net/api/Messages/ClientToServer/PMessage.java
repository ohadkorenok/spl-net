package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClient.ErrorMessage;
import bgu.spl.net.api.Messages.ServerToClient.NotificationMessage;
import bgu.spl.net.api.Messages.ServerToClient.ServerToClientNullMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class PMessage extends ClientToServerMessage {
    private static final int NUMBEROFARGS = 3;
    private static final short opCode = 6;
    private static final short notificationType = 0;
    private String content;
    private String userName;


    private final State state = State.PM;

    @Override
    public void decode(LinkedList<byte[]> args) {
        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- PM. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in PM -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
            userName= new String(args.get(1));
            content = new String(args.get(2));
        }
    }

    /**
     * Handle private message from producer to consumer(userName), if user is online the message is sent, if not it
     * will be added to pending list
     * @param db Database
     * @param connections master Connections
     * @param connectionId current ConnectionHandler id.
     * @return NullMessage if successful
     */
    @Override
    public ServerToClientMessage process(Database db, Connections connections, int connectionId) {
        ServerToClientMessage serverToClientMessage;
        User producer=fetchActiveUser(db,connectionId);
        if(producer==null)
            serverToClientMessage= new ErrorMessage(opCode);
        else{
            ServerToClientMessage msg=NotificationMessage.handleNotification(notificationType,producer,content,connections,db.getUser(userName));
            db.createMessage(producer,msg);
            serverToClientMessage = new ServerToClientNullMessage();
        }
        return serverToClientMessage;
    }
}
