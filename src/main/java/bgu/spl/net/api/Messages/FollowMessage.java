package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.Arrays;
import java.util.LinkedList;

public class FollowMessage extends Message {
    private int NUMBEROFARGS;
    private short opCode = 4;
    private boolean isUnfollow = false;
    private LinkedList<String> usersToFollow = new LinkedList<>();

    @Override
    public void act() {

    }

    @Override
    public void init(LinkedList<byte[]> args) {
        if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
            System.out.println("Error in Follow/Unfollow -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
        }
        isUnfollow = MessageEncoderDecoder.bytesToShort(args.get(1)) == 1;
        if (args.size() < 3) {
            System.out.println("Error in follow/unfollow!!!! the arrays is smaller than 3");
        }
        NUMBEROFARGS = MessageEncoderDecoder.bytesToShort(args.get(2));
        if (args.size() != NUMBEROFARGS+3) {
            System.out.println("ERROR in decode -- logout. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS+3);
        }
        for (int i = 3; i < i + NUMBEROFARGS; i++) {
            usersToFollow.add(new String(args.get(i)));
        }
    }
}