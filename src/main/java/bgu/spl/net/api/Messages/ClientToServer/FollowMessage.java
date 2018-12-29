package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClient.AckMessage;
import bgu.spl.net.api.Messages.ServerToClient.ErrorMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.bidi.MessageEncoderDecoder;
import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class FollowMessage extends ClientToServerMessage {
    private int NUMBEROFARGS;
    private short opCode = 4;
    private boolean isUnfollow = false;
    private LinkedList<String> usersToFollow = new LinkedList<>();
    private final State state = State.FOLLOWUNFOLLOW;
//    private int timeStamp;


    @Override
    public void decode(LinkedList<byte[]> args) {
        if (MessageEncoderDecoder.bytesToShort(args.get(0)) != opCode) {
            System.out.println("Error in Follow/Unfollow -- opcode!!! got " + MessageEncoderDecoder.bytesToShort(args.get(0)) + " Expected " + opCode);
        }
        isUnfollow = MessageEncoderDecoder.bytesToShort(args.get(1)) == 1;
        if (args.size() < 3) {
            System.out.println("Error in follow/unfollow!!!! the arrays is smaller than 3");
        }
        NUMBEROFARGS = MessageEncoderDecoder.bytesToShort(args.get(2));
        if (args.size() != NUMBEROFARGS + 3) {
            System.out.println("ERROR in decode -- logout. got " + args.size() + " arguments !!! expected : " + NUMBEROFARGS + 3);
        }
        for (int i = 3; i < i + NUMBEROFARGS; i++) {
            usersToFollow.add(new String(args.get(i)));
        }
    }

    /**Comparing between the producer followingList and the usersToFollow list, if absent(in case of follow)
     * or exist(in case of unfolllow) it will be added to difference list.
     * Also, if the difference is empty, it will return ErrorMessage.
     *
     * @param db Database
     * @param connection connections
     * @param connectionId current ConnectionHandler id.
     * @return Ack if successes or Error if difference.size is 0.
     */

    @Override
    public ServerToClientMessage process(Database db, Connections connection, int connectionId) {
        User user = ClientToServerMessage.fetchActiveUser(db, connectionId);
        if (user == null)
            return new ErrorMessage(opCode);
        else {
            LinkedList<String> difference = new LinkedList<>();
            LinkedList<User> followingList = user.getFollowing();
            LinkedList<String> followingString = User.userListToUserNameList(followingList);
            for (String usertoFollow : usersToFollow) {
                User userToFollowObj = db.getUser(usertoFollow);
                if (!followingString.contains(usertoFollow) && !isUnfollow) {
                    followingList.add(userToFollowObj);
                    userToFollowObj.addFollower(user);
                    difference.add(usertoFollow);
                } else if (followingString.contains(usertoFollow) && isUnfollow) {
                    followingList.remove(userToFollowObj);
                    userToFollowObj.removeFollower(user);
                    difference.add(usertoFollow);
                }
            }
            if (difference.size() == 0)
                return new ErrorMessage(opCode);
            else
                return new AckMessage(opCode, difference,(short)difference.size());
        }
    }
}

