package bgu.spl.net.impl.bidi;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClient.AckLogout;
import bgu.spl.net.api.Messages.ServerToClient.ServerToClientNullMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.Database;

public class BidiProtocol<Message> implements BidiMessagingProtocol<Message> {
    private Connections connections;
    private static Database db=new Database();
    private int connectionId;
    private boolean isTerminated;

    @Override
    public void start(int connectionId, Connections connections) {
        this.connections = connections;
        this.connectionId = connectionId;
        isTerminated=false;

    }

    @Override
    public void process(Message message) {
        boolean sendSucc=false;
        if (message instanceof ClientToServerMessage) {
            ServerToClientMessage response = ((ClientToServerMessage) message).process(db, connections, connectionId);
            if (!(response instanceof ServerToClientNullMessage)) {
                sendSucc = connections.send(connectionId, response);
                if ((response instanceof AckLogout) && sendSucc) {
                        handleLogout();
                    // TODO: what happens if I successed to push part of the bytes to my buffer, it returns true.
                    // TODO: add flag when we finished to put.
                    isTerminated = true;
                }
            }
        }
    }
    private void handleLogout() {
        User user = db.checkActiveUser(connectionId);
        if (user != null) {
            db.removeActiveUser(connectionId);
            user.setActiveConnectionId(-1);
            user.setIsActive(false);
        }
    }

    @Override
    public boolean shouldTerminate() {
        return isTerminated;
    }
}
