package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;

import java.util.LinkedList;

public class ServerToClientNullMessage extends ServerToClientMessage {

    private static final State state = State.NULLSTATE;
    @Override
    public byte[] encode() {
        System.out.println("ENCODING SERVER TO CLIENT NULL MESSAGE!!! ");
        return new byte[0];
    }

    @Override
    public void decode(LinkedList<byte[]> args) {

    }
}
