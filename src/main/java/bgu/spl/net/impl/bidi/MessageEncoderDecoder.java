package bgu.spl.net.impl.bidi;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ClientToServer.*;
import bgu.spl.net.api.Messages.ServerToClient.ServerToClientNullMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import javafx.util.Pair;
import bgu.spl.net.api.Messages.MessageFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class MessageEncoderDecoder implements bgu.spl.net.api.MessageEncoderDecoder<Message> {

    static HashMap<Short, Pair<Integer, Pair<State, Class<? extends ClientToServerMessage>>>> opCodeToState = new HashMap<>();

    private byte bytes[] = new byte[1 << 10];
    private static int followCounter = 0;
    private short opcode = 0;
    private int counter = 0;
    private LinkedList<byte[]> args = new LinkedList<>();
    private State state = State.NULLSTATE;
    private int zeroBytesRemaining = 0;
    private boolean isGivenOpcode = false;

    public MessageEncoderDecoder(){init();}
    public void init() {
        opCodeToState.put((short) 0, new Pair<>(-1,new Pair<>(State.NULLSTATE, ClientToServerNullMessage.class)));
        opCodeToState.put((short) 1, new Pair<>(2, new Pair<>(State.REGISTER, RegisterMessage.class)));
        opCodeToState.put((short) 2, new Pair<>(2, new Pair<>(State.LOGIN, LoginMessage.class)));
        opCodeToState.put((short) 3, new Pair<>(0, new Pair<>(State.LOGOUT, LogoutMessage.class)));
        opCodeToState.put((short) 4, new Pair<>(-1, new Pair<>(State.FOLLOWUNFOLLOW, FollowMessage.class)));
        opCodeToState.put((short) 5, new Pair<>(1, new Pair<>(State.POST, PostMessage.class)));
        opCodeToState.put((short) 6, new Pair<>(2, new Pair<>(State.PM, PMessage.class)));
        opCodeToState.put((short) 7, new Pair<>(0, new Pair<>(State.USERLIST, UserListMessage.class)));
        opCodeToState.put((short) 8, new Pair<>(1, new Pair<>(State.STAT, StatMessage.class)));
    }

    @Override
    public Message decodeNextByte(byte nextByte) {
        pushByte(nextByte);
        processByOpCode();
        if (zeroBytesRemaining == 0 && isGivenOpcode) {
            Message m = createMessage();
            resetAllData();
            return m;
        }
        return null;
    }

    private void resetAllData() {
        bytes = new byte[1 << 10];
        counter = 0;
        opcode = 0;
        args = new LinkedList<>();
        zeroBytesRemaining = 0;
        isGivenOpcode = false;
    }

    /**
     * This method adds the bytes segment to the args array and initializes the segment (bytes array) .
     */
    private void updateArgs() {
        byte [] argByte = new byte[counter+1];
        for (int i = 0; i < counter; i++) {
        argByte[i] = bytes[i];
        }
        args.add(argByte);
        bytes = new byte[1 << 10];
        counter = 0;
    }

    private Message createMessage() {
        Class<? extends ClientToServerMessage> messageClass = opCodeToState.get(opcode).getValue().getValue();
        MessageFactory m = new MessageFactory();
        return m.get(args, messageClass);
    }

    private void processByOpCode() {
        if (counter == 2 && !isGivenOpcode) {
            short opCode = bytesToShort(bytes);
            this.opcode = opCode;
                Pair<Integer, Pair<State, Class<? extends ClientToServerMessage>>> pair = opCodeToState.get((short)opCode);
                state = pair.getValue().getKey();
            zeroBytesRemaining = pair.getKey();
            isGivenOpcode = true;
            updateArgs();
        }
    }

    /**
     * This method encodes a message, only if it throws
     * @param message the message to encode
     * @return byte[] message by bytes or null.
     */
    @Override
    public byte[] encode(Message message) {
        byte[] encodedMessage;
        if (message instanceof ServerToClientMessage) {
            encodedMessage = ((ServerToClientMessage) message).encode();
        }
        else{
            System.out.println("Tried to encode a message that is a client to server message. The class of the message is:  "+message.getClass().getName().toString());
            encodedMessage = new ServerToClientNullMessage().encode();
        }
        return encodedMessage;
    }

    public static short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }

    public static byte[] shortToBytes(short num) {
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

        if (nextByte != '\0' || !isGivenOpcode) {
            if (counter >= bytes.length) {
                bytes = Arrays.copyOf(bytes, counter * 2);
            }
            bytes[counter] = nextByte;
            counter++;
        } else if ((zeroBytesRemaining > 0 && isGivenOpcode)) {
            zeroBytesRemaining--;
            updateArgs();
        }

        if (state == State.FOLLOWUNFOLLOW) {
            if (followCounter == 1) {
                updateArgs();
            } else if (followCounter == 3) {
                zeroBytesRemaining = bytesToShort(bytes);
                updateArgs();
            }
            followCounter++;
        }
    }
}
