package bgu.spl.net.impl.bidi;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
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
            ServerToClientMessage response = ((ClientToServerMessage) message).process();

        }
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
