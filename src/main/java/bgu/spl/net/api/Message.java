package bgu.spl.net.api;

import bgu.spl.net.api.bidi.Connections;

import java.util.LinkedList;

public abstract class Message<T> {
    private Connections<T> connList;

    public void act(){};

    public Message(LinkedList<byte[]> args){}
}
