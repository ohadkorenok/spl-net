package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.bidi.ConnectionHandler;
import bgu.spl.net.srv.bidi.Database;

import java.util.HashMap;

public class ConnectionsImplTPC implements Connections {

    private static int idToInsert = 0;
    private HashMap<Integer, ConnectionHandler> activeClients = new HashMap<>();
    private Database dB=new Database();

    public void pushHandler(BlockingConnectionHandler toPush){
        activeClients.put(idToInsert,toPush);
        idToInsert++;
    }

    @Override
    public boolean send(int connectionId, Object msg) {
        return false;
    }

    @Override
    public void broadcast(Object msg) {

    }

    @Override
    public void disconnect(int connectionId) {

    }
}
