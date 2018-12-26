package bgu.spl.net.impl.bidi;

import bgu.spl.net.api.Message;
import bgu.spl.net.api.Messages.LoginMessage;
import bgu.spl.net.api.Messages.LogoutMessage;
import bgu.spl.net.api.Messages.RegisterMessage;
import bgu.spl.net.api.State;
import javafx.util.Pair;
import bgu.spl.net.api.MessageFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class MessageEncoderDecoder implements bgu.spl.net.api.MessageEncoderDecoder {

    static HashMap<Short, Pair<Integer, Pair<State, Class<? extends Message>>>> opCodeToState = new HashMap<>();

    private byte bytes[] = new byte[1 << 10];
    private short opcode = 0;
    private int counter = 0;
    private LinkedList<byte[]> args = new LinkedList<>();
    private State state = State.NULLSTATE;
    private int zeroBytesRemaining = 0;
    private boolean isGivenOpcode = false;

    private void init() {
        opCodeToState.put((short) 0, new Pair<>(-1, State.NULLSTATE));
        opCodeToState.put((short) 1, new Pair<>(2, new Pair<>(State.REGISTER, RegisterMessage.class)));
        opCodeToState.put((short) 2, new Pair<>(2, new Pair<>(State.LOGIN, LoginMessage.class)));
        opCodeToState.put((short) 3, new Pair<>(0, new Pair<>(State.LOGOUT, LogoutMessage.class)));
        opCodeToState.put((short) 4, new Pair<>(-1, State.FOLLOWUNFOLLOW));
        opCodeToState.put((short) 5, new Pair<>(1, State.POST));
        opCodeToState.put((short) 6, new Pair<>(2, State.PM));
        opCodeToState.put((short) 7, new Pair<>(0, State.USERLIST));
        opCodeToState.put((short) 8, new Pair<>(1, State.STAT));
        opCodeToState.put((short) 9, new Pair<>(-1, State.NOTIFICATION));
        opCodeToState.put((short) 10, new Pair<>(-1, State.ACK));
        opCodeToState.put((short) 11, new Pair<>(-1, State.ERROR));
    }

    @Override
    public Object decodeNextByte(byte nextByte) {
        if (opCodeToState.isEmpty()) {
            init();
        }
        pushByte(nextByte);
        processByOpCode();
        createMessage();
    }

    /**
     * This method adds the bytes segment to the args array and initializes the segment (bytes array) .
     */
    private void updateArgs() {
        args.add(bytes);
        bytes = new byte[1 << 10];
        counter = 0;
    }

    private Message createMessage() {
        if (zeroBytesRemaining == 0 && isGivenOpcode) {
            Class <? extends Message> messageClass = opCodeToState.get(opcode).getValue().getValue();
            MessageFactory m = new MessageFactory() ;
            m.get(args, messageClass);
        }
    }

    private void processByOpCode() {
        if (counter == 2) {
            short opCode = bytesToShort(bytes);
            this.opcode = opCode;
            Pair<Integer, State> pair = opCodeToState.get(opCode);
            state = pair.getValue();
            zeroBytesRemaining = pair.getKey();
            isGivenOpcode = true;
            updateArgs();
            bytes = new byte[1 << 10];
            counter = 0;
        }
    }

    @Override
    public byte[] encode(Object message) {
        return new byte[0];
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }

    public byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }

    /**
     * This function pushes the byte into the bytes array.
     * if the next byte
     *
     * @param nextByte byte
     */
    private void pushByte(byte nextByte) {
        if (nextByte != '\0') {
            if (counter >= bytes.length) {
                bytes = Arrays.copyOf(bytes, counter * 2);
            }
            bytes[counter] = nextByte;
            counter++;
        } else if (zeroBytesRemaining > 0 && isGivenOpcode) {
            zeroBytesRemaining--;
            updateArgs();

        }
    }
}
