package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.Connections;

import java.util.LinkedList;
import java.util.Set;

public class NotificationMessage extends ServerToClientMessage {

    private final short opCode = 9;
    private short notificationType;
    private String postingUser;
    private String content;
    private final State state = State.NOTIFICATION;


    @Override
    public void decode(LinkedList<byte[]> args) {
    }

    public NotificationMessage(short notificationType, String postingUser, String content) {
        this.notificationType = notificationType;
        this.postingUser = postingUser;
        this.content = content;
    }

    public byte[] encode() {
        LinkedList<Byte> byteLinkedList = new LinkedList<>();
        convertShortToByteAndPushToLinkedList(byteLinkedList, opCode);
        convertShortToByteAndPushToLinkedList(byteLinkedList, notificationType);
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
    private static NotificationMessage handleNotification(short notificationType, User producer, String content, Connections connections, User follower) {
        NotificationMessage notificationMessage = new NotificationMessage(notificationType, producer.getUserName(), content);
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
    public static ServerToClientMessage sendNotificationsToRecipients(short notificationType, User producer, Set<User> recipients, String content, Connections connections) {
        ServerToClientMessage notificationMessage = new ServerToClientNullMessage();
        for (User recipient :
                recipients) {
            notificationMessage = handleNotification(notificationType, producer, content, connections, recipient);
        }
        return notificationMessage;
    }

}
