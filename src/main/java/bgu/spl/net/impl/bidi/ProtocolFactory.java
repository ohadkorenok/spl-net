package bgu.spl.net.impl.bidi;

import bgu.spl.net.srv.Database;

import java.util.function.Supplier;

public class ProtocolFactory implements Supplier {
//    private Database db = new Database();

    @Override
    public Object get() {
        BidiProtocol bidiProtocol = new BidiProtocol();
        return bidiProtocol;
    }
}
