package bgu.spl.net.api.Messages.ServerToClient;


import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class ErrorMessage extends ServerToClientMessage {
    private final short opCode = 11;
    private short errMessageOpcode;
    private final State state = State.ERROR;

    public ErrorMessage(short errMessageOpcode) {
        this.errMessageOpcode = errMessageOpcode;
    }

    public byte[] encode() {
        byte[] opCodetoByte = MessageEncoderDecoder.shortToBytes(opCode);
        byte[] errmessOpcode = MessageEncoderDecoder.shortToBytes(errMessageOpcode);
        byte[] toRet = new byte[(opCodetoByte.length) + (errmessOpcode.length)];
        for (int i = 0; i < opCodetoByte.length; i++) {
            toRet[i] = opCodetoByte[i];
        }
        for (int i = 0; i < errmessOpcode.length; i++) {
            toRet[i + opCodetoByte.length] = errmessOpcode[i];
        }
        return toRet;
    }

    @Override
    public void decode(LinkedList<byte[]> args) {

    }
}
