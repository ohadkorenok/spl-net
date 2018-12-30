package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;

import java.util.LinkedList;

public class AckMessage extends ServerToClientMessage {
    private short messageOpCode;
    private LinkedList<String> args;
    private short numOfusers;
    private final short opCode = 10;
    private final State state = State.ACK;

    public LinkedList<String> getArgs() {
        return args;
    }

    public AckMessage(short messageOpCode, LinkedList<String> args) {
        this.messageOpCode = messageOpCode;
        this.args = args;
        numOfusers=-1;
    }
    public AckMessage(short messageOpCode, LinkedList<String> args,short numOfusers){
        this.messageOpCode = messageOpCode;
        this.args = args;
        this.numOfusers=numOfusers;
    }

    @Override
    public void decode(LinkedList<byte[]> args) {

    }

    /**
     * this function encodes the message into byte[] array.
     *
     * @return
     */
    public byte[] encode() {
        LinkedList<Byte> byteLinkedList = new LinkedList<>();
        this.convertShortToByteAndPushToLinkedList(byteLinkedList, opCode);
        this.convertShortToByteAndPushToLinkedList(byteLinkedList, messageOpCode);
        if(numOfusers>-1){
            this.convertShortToByteAndPushToLinkedList(byteLinkedList,numOfusers);
        }
        for (String argument :
                args) {
            this.convertStringToByteAndPushToLinkedList(byteLinkedList, argument);
        }
        return fromByteLinkedListToByteArray(byteLinkedList);
    }
}
