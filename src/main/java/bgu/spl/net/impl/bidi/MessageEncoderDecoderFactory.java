package bgu.spl.net.impl.bidi;

import java.util.function.Supplier;

public class MessageEncoderDecoderFactory implements Supplier {
    @Override
    public MessageEncoderDecoder get() {
        MessageEncoderDecoder toReturn = new MessageEncoderDecoder();
        toReturn.init();
        return toReturn;
    }
}
