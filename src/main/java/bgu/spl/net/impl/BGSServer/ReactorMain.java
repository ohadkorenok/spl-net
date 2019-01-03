package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoderFactory;
import bgu.spl.net.impl.bidi.ProtocolFactory;
import bgu.spl.net.srv.Reactor;

public class ReactorMain {
    public static void main(String[] args){
        MessageEncoderDecoderFactory encdecFac=new MessageEncoderDecoderFactory();
        ProtocolFactory protocolFactory=new ProtocolFactory();
        Reactor<Message> react=new Reactor<>(Integer.parseInt(args[1]),Integer.parseInt(args[0]),protocolFactory,encdecFac);
        react.serve();
    }
}
