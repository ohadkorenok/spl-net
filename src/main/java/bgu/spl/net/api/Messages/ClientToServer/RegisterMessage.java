package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.AckMessage;
import bgu.spl.net.api.Messages.ServerToClient.ErrorMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class RegisterMessage extends ClientToServerMessage {
    private static final int NUMBEROFARGS = 3;
    private static final short opCode = 1;
    private String userName;
    private String password;
    private Database db;

    @Override
    public Message process() {
        Message message = new ClientToServerNullMessage();
        boolean success = db.createUser(new User(userName, password));
        if(success){
            message = new AckMessage(opCode, new LinkedList<>());
        }
        else{
            message = new ErrorMessage(opCode);
        }
        return message;
    }

    private final State state = State.REGISTER;


    @Override
    public void decode(LinkedList<byte[]> args) {
        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- register. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in Register -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
            userName = new String(args.get(1));
            password = new String(args.get(2));
        }
    }
}
