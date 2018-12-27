package bgu.spl.net;

import bgu.spl.net.api.Messages.ServerToClient.AckMessage;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.impl.bidi.MessageEncoderDecoderFactory;

import java.util.LinkedList;

public class main {
    public static void main(String[] args) {
        MessageEncoderDecoderFactory factory = new MessageEncoderDecoderFactory();
        MessageEncoderDecoder mED = factory.get();
        AckMessage ackMessage = new AckMessage("login", (short)1, new LinkedList<String>());
        byte [] ackBytes = ackMessage.encode();
        String ohad = "ohad";
    }
}
