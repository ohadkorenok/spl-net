package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;

import java.util.LinkedList;

public class NotificationMessage extends ServerToClientMessage {

    private final short opCode = 9;
    private short notificationType;
    private String postingUser;
    private String content;
    private final State state = State.NOTIFICATION;


    @Override
    public void decode(LinkedList<byte[]> args) {
    }

    public NotificationMessage(short notificationType, String postingUser, String content) {
        this.notificationType = notificationType;
        this.postingUser = postingUser;
        this.content = content;
    }

    public byte[] encode() {
        LinkedList<Byte> byteLinkedList = new LinkedList<>();
        convertShortToByteAndPushToLinkedList(byteLinkedList, opCode);
        convertShortToByteAndPushToLinkedList(byteLinkedList, notificationType);
        convertStringToByteAndPushToLinkedList(byteLinkedList, postingUser);
        convertStringToByteAndPushToLinkedList(byteLinkedList, content);
        return fromByteLinkedListToByteArray(byteLinkedList);
    }

}
