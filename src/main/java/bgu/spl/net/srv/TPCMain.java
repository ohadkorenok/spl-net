package bgu.spl.net.srv;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoderFactory;
import bgu.spl.net.impl.bidi.ProtocolFactory;

public class TPCMain {
    public static void main(String[] args){
        MessageEncoderDecoderFactory encdecFac=new MessageEncoderDecoderFactory();
        ProtocolFactory protocolFactory=new ProtocolFactory();
        ThreadsPerClient<Message> tpc=new ThreadsPerClient<>(Integer.parseInt(args[1]),protocolFactory,encdecFac);
        tpc.serve();
    }
}
