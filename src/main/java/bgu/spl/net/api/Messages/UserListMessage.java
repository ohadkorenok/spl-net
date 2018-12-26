package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class UserListMessage extends Message {
    private int NUMBEROFARGS = 1;
    private static final short opCode = 7;
    @Override
    public void act() {

    }

    @Override
    public void init(LinkedList <byte[]> args) {

        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- USERLIST. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in USERLIST -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
        }

    }
}
