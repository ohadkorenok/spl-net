package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;

import java.util.LinkedList;
import java.util.Set;

public class NotificationMessage extends ServerToClientMessage implements Comparable {

    private final short opCode = 9;
    private short notificationType;
    private byte type;
    private String postingUser;
    private String content;
    private final State state = State.NOTIFICATION;
    private final int messageId;


    @Override
    public void decode(LinkedList<byte[]> args) {
    }

    public int getMessageId() {
        return messageId;
    }

    public NotificationMessage(short notificationType, String postingUser, String content, int messageId) {
        this.notificationType = notificationType;
        if(notificationType==0)
            type=0;
        else if(notificationType==1)
            type=1;
        this.postingUser = postingUser;
        this.content = content;
        this.messageId = messageId;
    }

    public byte[] encode() {
        LinkedList<Byte> byteLinkedList = new LinkedList<>();
        convertShortToByteAndPushToLinkedList(byteLinkedList, opCode);
        byteLinkedList.add(type);
        convertStringToByteAndPushToLinkedList(byteLinkedList, postingUser);
        convertStringToByteAndPushToLinkedList(byteLinkedList, content);
        return fromByteLinkedListToByteArray(byteLinkedList);
    }

    /**
     * This method creates a notification message, and sends it to the specific user
     * @param notificationType 0/1 PM/POST
     * @param producer User user that initiates the message
     * @param content String
     * @param connections Connections
     * @param follower User target user.
     */
    public static NotificationMessage handleNotification(short notificationType, User producer, String content, Connections connections, User follower, int messageId) {
        NotificationMessage notificationMessage = new NotificationMessage(notificationType, producer.getUserName(), content, messageId);
        sendNotification(notificationMessage, connections, follower);
        return notificationMessage;
    }


    /**
     * This method checks whether the target user is online/offline. if he is online it sends a notification, if
     * offline, adds to pendingNotifications list of the user.
     * @param notificationMessage NotificationMessage
     * @param connections Connections
     * @param toNotify User target user.
     */
    private static void sendNotification(NotificationMessage notificationMessage, Connections connections, User toNotify) {
        if (toNotify.isActive()) {
            if (!connections.send(toNotify.getActiveConnectionId(), notificationMessage)) {
                System.out.println("Couldnt send this notification, even though the user is online!!! added ");
                toNotify.addNotification(notificationMessage);
            }
        } else {
            toNotify.addNotification(notificationMessage);
        }

    }

    /**
     * This method sends notifcations to all of the recipients in the set.
     * @param notificationType 0/1 PM/POST
     * @param producer User
     * @param recipients Set<User> list of recipients
     * @param content String
     * @param connections Connections
     */
    public static ServerToClientMessage handleNotificationToRecipients(short notificationType, User producer, Set<User> recipients, String content, Connections connections, int messageId) {
        ServerToClientMessage notificationMessage = new ServerToClientNullMessage();
        for (User recipient :
                recipients) {
            notificationMessage = handleNotification(notificationType, producer, content, connections, recipient, messageId);
        }
        return notificationMessage;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof NotificationMessage)){
            throw new IllegalArgumentException("Error! these objects are not comparable! got "+o.getClass().getName()+"  class");
        }
        else{
            int otherMessageId = ((NotificationMessage) o).getMessageId();
            return   messageId - otherMessageId;
        }
    }
}
