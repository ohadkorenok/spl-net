package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class PostMessage extends Message {
    private static final int NUMBEROFARGS = 2;
    private static final short opCode = 5;
    private String content;

    @Override
    public void act() {

    }

    @Override
    public void init(LinkedList<byte[]> args) {
        if (args.size() != NUMBEROFARGS) {
            System.out.println("ERROR in decode -- POST. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS);
        } else {
            if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
                System.out.println("Error in POST -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
            }
            content = new String(args.get(1));
        }
    }
}
