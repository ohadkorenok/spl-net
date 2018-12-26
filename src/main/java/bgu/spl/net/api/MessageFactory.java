package bgu.spl.net.api;

import bgu.spl.net.api.Messages.NullMessage;

import java.util.LinkedList;

public class MessageFactory {

    public MessageFactory() {

    }

    public Message get(LinkedList<byte[]> args, Class<? extends Message> messageClass) {
        Message m = new NullMessage();
        try {
            m = messageClass.newInstance();
            m.init(args);
            return m;
        } catch (IllegalAccessException e) {
            System.out.println("illegal accessException");
        } catch (InstantiationException e) {
            System.out.println("instantiation exception");
        }
        return m;
    }

}
