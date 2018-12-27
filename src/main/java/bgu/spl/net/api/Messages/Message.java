package bgu.spl.net.api.Messages;

import bgu.spl.net.api.State;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public abstract class Message {
    Connections connections;
    protected State state;


    public abstract void act();

    public Message() {
    }

    public abstract void decode(LinkedList<byte[]> args);


    //TODO:: ADD WALLS AFTER EACH WORD!!!!!
    protected void convertShortToByteAndPushToLinkedList(LinkedList<Byte> byteLinkedList, short toConvert) {
        byte[] temp = MessageEncoderDecoder.shortToBytes(toConvert);
        for (int i = 0; i < temp.length; i++) {
            byteLinkedList.add(temp[i]);
        }
    }

    //TODO:: ADD WALLS AFTER EACH WORD!!!!!
    protected void convertStringToByteAndPushToLinkedList(LinkedList<Byte> byteLinkedList, String toConvert) {
        toConvert += "\'0'";
        byte[] temp = toConvert.getBytes(StandardCharsets.UTF_8); //TODO:: check for sure if the function adds '\0' to the byte array.
        for (int i = 0; i < temp.length; i++) {
            byteLinkedList.add(temp[i]);
        }
    }

    //TODO:: ADD WALLS AFTER EACH WORD!!!!!
    protected byte[] fromByteLinkedListToByteArray(LinkedList<Byte> byteLinkedList) {
        byte[] bytes = new byte[byteLinkedList.size()];
        for (int i = 0; i < byteLinkedList.size(); i++) {
            bytes[i] = byteLinkedList.get(i);
        }
        return bytes;
    }

}
