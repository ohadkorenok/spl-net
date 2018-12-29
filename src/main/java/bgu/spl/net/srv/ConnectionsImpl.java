package bgu.spl.net.srv;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.util.HashMap;

public class ConnectionsImpl<T> implements Connections<T> {

    private static int idToInsert = 0;
    private HashMap<Integer, ConnectionHandler<T>> activeClients = new HashMap<>();

    public void pushHandler(BlockingConnectionHandler<T> toPush) {
        activeClients.put(idToInsert, toPush);
        idToInsert++;
    }

    @Override
    public boolean send(int connectionId, T msg) {
        ConnectionHandler <T> connectionHandler = this.activeClients.getOrDefault(connectionId, null);
        if (connectionHandler != null && msg !=null) {
            connectionHandler.send(msg);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void broadcast(T msg) {
        synchronized (activeClients) {
            for (ConnectionHandler<T> connectionHandler :
                    activeClients.values()) {
                connectionHandler.send(msg);
            }
        }

    }

    @Override
    public void disconnect(int connectionId) {

    }
}
