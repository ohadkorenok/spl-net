package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class StatMessage extends ClientToServerMessage {
    private int NUMBEROFARGS = 2;
    private static final short opCode = 8;
    private String userName;
    private final State state = State.STAT;


    @Override
    public void act() {

    }

    @Override
    public void decode(LinkedList <byte[]> args) {
        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- STAT . got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in STAT -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
            userName = new String(args.get(1));
        }
    }
}
