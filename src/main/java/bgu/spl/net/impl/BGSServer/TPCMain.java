package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.impl.bidi.MessageEncoderDecoderFactory;
import bgu.spl.net.impl.bidi.ProtocolFactory;
import bgu.spl.net.srv.ThreadsPerClient;

public class TPCMain {
    public static void main(String[] args){
        MessageEncoderDecoderFactory encdecFac=new MessageEncoderDecoderFactory();
        ProtocolFactory protocolFactory=new ProtocolFactory();
        ThreadsPerClient<Message> tpc=new ThreadsPerClient<>(Integer.parseInt(args[0]),protocolFactory,encdecFac);
        tpc.serve();
    }
}
