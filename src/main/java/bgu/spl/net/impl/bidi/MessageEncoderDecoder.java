package bgu.spl.net.impl.bidi;

public class MessageEncoderDecoder implements bgu.spl.net.api.MessageEncoderDecoder {

    private byte bytes [] = new byte[1<<10];

    @Override
    public Object decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(Object message) {
        return new byte[0];
    }
}
