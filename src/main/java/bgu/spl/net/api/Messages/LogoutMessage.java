package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class LogoutMessage extends Message {

    private static final int NUMBEROFARGS = 1;
    private static final short opCode = 3;


    @Override
    public void init(LinkedList<byte[]> args) {
        if(args.size() != NUMBEROFARGS){
            System.out.println("ERROR in decode -- logout. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        }
        if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
            System.out.println("Error in Logout -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
        }

    }

    @Override
    public void act() {

    }
}
