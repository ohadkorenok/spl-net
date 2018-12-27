package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class LoginMessage extends ClientToServerMessage {

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

    @Override
    public ServerToClientMessage process() {
        return null;
    }
}

