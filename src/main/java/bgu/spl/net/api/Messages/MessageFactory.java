package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Messages.ClientToServer.ClientToServerNullMessage;
import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class MessageFactory {
    public MessageFactory() {
    }

    public Message get(LinkedList<byte[]> args, Class<? extends ClientToServerMessage> messageClass) {
        Message m = new ClientToServerNullMessage();
        try {
            m = messageClass.newInstance();
            m.decode(args);
            return m;
        } catch (IllegalAccessException e) {
            System.out.println("illegal accessException");
        } catch (InstantiationException e) {
            System.out.println("instantiation exception");
        }
        return m;
    }

}
