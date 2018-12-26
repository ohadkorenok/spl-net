package bgu.spl.net.api.Messages;


import bgu.spl.net.api.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.util.LinkedList;

public class ErrorMessage extends Message {
    private final short opCode = 11;
    private short errmessageOpcode;

    @Override
    public void act() {

    }
    public ErrorMessage(short errmessageOpcode){
        this.errmessageOpcode=errmessageOpcode;
    }
    public byte[] encode(){
        byte[] opCodetoByte= MessageEncoderDecoder.shortToBytes(opCode);
        byte[] errmessOpcode=MessageEncoderDecoder.shortToBytes(errmessageOpcode);
        byte[] toRet=new byte[(opCodetoByte.length)+(errmessOpcode.length)];
        for(int i=0;i<opCodetoByte.length;i++){
            toRet[i]=opCodetoByte[i];
        }
        for(int i=0;i<errmessOpcode.length;i++){
            toRet[i+opCodetoByte.length]=errmessOpcode[i];
        }
        return toRet;
    }
    @Override
    public void init(LinkedList<byte[]> args) {

    }
}
