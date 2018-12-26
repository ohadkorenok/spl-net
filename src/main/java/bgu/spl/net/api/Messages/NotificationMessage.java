package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class NotificationMessage extends Message {

    private final short opCode = 9;
    private short notificationType;
    private String postingUser;
    private String content;

    @Override
    public void act() {

    }

    @Override
    public void init(LinkedList<byte[]> args) {
    }

    public NotificationMessage(short notificationType, String postingUser, String content) {
        this.notificationType = notificationType;
        this.postingUser = postingUser;
        this.content = content;
    }

    public byte[] encode() {
        LinkedList<Byte> byteLinkedList = new LinkedList<>();
//        byte[] bytes = new byte[1 << 10];
        convertShortToByteAndPushToLinkedList(byteLinkedList, opCode);
        convertShortToByteAndPushToLinkedList(byteLinkedList, notificationType);
        convertStringToByteAndPushToLinkedList(byteLinkedList, postingUser);
        convertStringToByteAndPushToLinkedList(byteLinkedList, content);
        byte[] bytes = byteLinkedList.toArray();
    }

    private void convertShortToByteAndPushToLinkedList(LinkedList<Byte> byteLinkedList, short toConvert) {
        byte[] temp = MessageEncoderDecoder.shortToBytes(toConvert);
        for (int i = 0; i < temp.length; i++) {
            byteLinkedList.add(temp[i]);
        }
    }

    private void convertStringToByteAndPushToLinkedList(LinkedList<Byte> byteLinkedList, String toConvert) {
        byte[] temp = toConvert.getBytes(StandardCharsets.UTF_8); //TODO:: check for sure if the function adds '\0' to the byte array.
        for (int i = 0; i < temp.length; i++) {
            byteLinkedList.add(temp[i]);
        }
    }
    }
