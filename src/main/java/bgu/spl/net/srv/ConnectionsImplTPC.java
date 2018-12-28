package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.util.HashMap;

public class ConnectionsImplTPC implements Connections {

    private static int idToInsert = 0;
    private HashMap<Integer, ConnectionHandler> activeClients = new HashMap<>();

    public void pushHandler(BlockingConnectionHandler toPush){
        activeClients.put(idToInsert,toPush);
        idToInsert++;
    }

    @Override
    public boolean send(int connectionId, Object msg) {
        ConnectionHandler connectionHandler = this.activeClients.getOrDefault(connectionId, null);
        if(connectionHandler!=null){
            connectionHandler.send(msg);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void broadcast(Object msg) {

    }

    @Override
    public void disconnect(int connectionId) {

    }
}
