package bgu.spl.net.impl.bidi;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

public class BidiProtocol implements BidiMessagingProtocol {
    private Connections connections;

    @Override
    public void start(int connectionId, Connections connections) {

    }

    @Override
    public void process(Object message) {
        if (message instanceof ClientToServerMessage){
            ((ClientToServerMessage) message).process(this.connections.getDatabase());
        }
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
