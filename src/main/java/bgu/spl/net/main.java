package bgu.spl.net;

import bgu.spl.net.impl.bidi.MessageEncoderDecoderFactory;
import bgu.spl.net.impl.bidi.ProtocolFactory;
import bgu.spl.net.srv.Server;


public class main {
    public static void main(String[] args) {
        MessageEncoderDecoderFactory encdecFac=new MessageEncoderDecoderFactory();
        ProtocolFactory protocolFactory=new ProtocolFactory();
        Server.threadPerClient(7777,protocolFactory,encdecFac).serve();
//        Server.reactor(10,7777,protocolFactory,encdecFac).serve();
//        MessageEncoderDecoderFactory factory = new MessageEncoderDecoderFactory();
//        MessageEncoderDecoder mED = factory.get();
//        AckMessage ackMessage = new AckMessage("login", (short)1, new LinkedList<String>());
//        byte [] ackBytes = ackMessage.encode();
//        String ohad = "ohad";
    }
}
