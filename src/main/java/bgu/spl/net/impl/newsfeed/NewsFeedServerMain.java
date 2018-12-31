package bgu.spl.net.impl.newsfeed;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.impl.bidi.BidiProtocol;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.impl.bidi.MessageEncoderDecoderFactory;
import bgu.spl.net.impl.bidi.ProtocolFactory;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
import bgu.spl.net.srv.Server;

public class NewsFeedServerMain {

    public static void main(String[] args) {
//        NewsFeed feed = new NewsFeed(); //one shared object
        MessageEncoderDecoderFactory encdecFac=new MessageEncoderDecoderFactory();
        ProtocolFactory protocolFactory=new ProtocolFactory();

// you can use any server...
        Server.threadPerClient(7777,protocolFactory,encdecFac).serve();

//        Server.threadPerClient(
//                7777, //port
//                () -> new BidiProtocol(), //protocol factory
//                MessageEncoderDecoder::new //message encoder decoder factory
//        ).serve();

//        Server.reactor(
//                Runtime.getRuntime().availableProcessors(),
//                7777, //port
//                () ->  new RemoteCommandInvocationProtocol<>(feed), //protocol factory
//                ObjectEncoderDecoder::new //message encoder decoder factory
//        ).serve();
//
    }
}
