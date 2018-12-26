package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class PMessage extends Message {
    private static final int NUMBEROFARGS = 3;
    private static final short opCode = 6;
    private String content;
    private String userName;
    @Override
    public void act() {

    }

    @Override
    public void init(LinkedList<byte[]> args) {
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
}
