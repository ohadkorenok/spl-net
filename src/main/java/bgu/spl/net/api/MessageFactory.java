package bgu.spl.net.api;

import java.util.LinkedList;

public class MessageFactory {

    public MessageFactory() {

    }

    public Message get(LinkedList<byte[]> args, Class <? extends Message> messageClass){
        messageClass.getConstructor(args);
    }

}
