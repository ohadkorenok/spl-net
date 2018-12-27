package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class LogoutMessage extends ClientToServerMessage {

    private static final int NUMBEROFARGS = 1;
    private static final short opCode = 3;
    private final State state = State.LOGOUT;



    @Override
    public void decode(LinkedList<byte[]> args) {
        if(args.size() != NUMBEROFARGS){
            System.out.println("ERROR in decode -- logout. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        }
        if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
            System.out.println("Error in Logout -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
        }

    }

    @Override
    public ServerToClientMessage process() {
        return null;
    }
}