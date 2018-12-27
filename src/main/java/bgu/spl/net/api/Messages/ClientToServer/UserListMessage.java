package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class UserListMessage extends ClientToServerMessage {
    private int NUMBEROFARGS = 1;
    private static final short opCode = 7;
    private final State state = State.USERLIST;

    @Override
    public void act() {

    }

    @Override
    public void decode(LinkedList <byte[]> args) {

        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- USERLIST. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in USERLIST -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
        }

    }
}
