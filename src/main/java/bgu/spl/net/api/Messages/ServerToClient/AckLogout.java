package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;

import java.util.LinkedList;

public class AckLogout extends ServerToClientMessage {
    private short messageOpCode;
    private final short opCode = 10;
    private final State state = State.ACK;

    @Override
    public void decode(LinkedList<byte[]> args) {
    }
    @Override
    public byte[] encode() {
        LinkedList<Byte> byteLinkedList = new LinkedList<>();
        this.convertShortToByteAndPushToLinkedList(byteLinkedList, opCode);
        this.convertShortToByteAndPushToLinkedList(byteLinkedList, messageOpCode);
        return fromByteLinkedListToByteArray(byteLinkedList);
    }
}
