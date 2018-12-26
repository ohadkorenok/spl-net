package bgu.spl.net.api;

import bgu.spl.net.api.bidi.Connections;

public abstract class Message<T> {
    private Connections<T> connList;

    public void act(){};
}
