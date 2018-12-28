package bgu.spl.net.api.Messages;

import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.Database;

public abstract class ClientToServerMessage extends Message {

    public abstract ServerToClientMessage process(Database db, Connections connection , int connectionId);

    /**
     * This method fetches the user that logged in to the connection. . Returns null if no active user found.
     * @param db
     * @param connId
     * @return
     */
    protected static User fetchActiveUser(Database db, int connId){
        return db.checkActiveUser(connId);
    }
}
