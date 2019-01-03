package bgu.spl.net.srv;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoderFactory;
import bgu.spl.net.impl.bidi.ProtocolFactory;

public class ReactorMain {
    public static void main(String[] args){
        MessageEncoderDecoderFactory encdecFac=new MessageEncoderDecoderFactory();
        ProtocolFactory protocolFactory=new ProtocolFactory();
        Reactor<Message> react=new Reactor<>(Integer.parseInt(args[2]),Integer.parseInt(args[1]),protocolFactory,encdecFac);
        react.serve();
    }
}
