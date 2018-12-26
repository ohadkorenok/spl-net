package bgu.spl.net.api;

import bgu.spl.net.api.bidi.Connections;

import java.util.LinkedList;

public abstract class Message {
    Connections connections;
    enum type {ServerToClient,ClientToServer};

    public abstract void act();

    public Message() {
    }

    public abstract void init(LinkedList<byte[]> args);

}
