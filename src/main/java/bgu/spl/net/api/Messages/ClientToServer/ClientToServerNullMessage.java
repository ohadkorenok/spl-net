package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.State;

import java.util.LinkedList;

public class ClientToServerNullMessage extends ClientToServerMessage {

    private static final State state = State.NULLSTATE;

    @Override
    public void act() {

    }

    @Override
    public void decode(LinkedList args) {
        System.out.println("NULL MESSAGE");
    }
}
