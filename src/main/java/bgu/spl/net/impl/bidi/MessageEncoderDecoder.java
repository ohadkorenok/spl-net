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

    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }
}
