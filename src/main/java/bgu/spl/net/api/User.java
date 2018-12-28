package bgu.spl.net.api;

import java.util.LinkedList;

public class User {
    private String userName;
    private String password;
    private boolean isActive = false;
    private LinkedList<String> myFollowList;

    public User(String userName, String password, boolean isActive){
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.myFollowList=new LinkedList<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param comparing
     * @param isUnfollow
     * @return
     */
    public LinkedList<String> compareSetAndDifference(LinkedList<String> comparing, boolean isUnfollow){
        LinkedList<String> difference=new LinkedList<String>();
        for (String usertoFollow: comparing) {
            if(!myFollowList.contains(usertoFollow) && !isUnfollow) {
                myFollowList.add(usertoFollow);
                difference.add(usertoFollow);
            }
            else if(myFollowList.contains(usertoFollow) && isUnfollow){
                myFollowList.remove(usertoFollow);
                difference.add(usertoFollow);
            }

        }
        return difference;
    }



}
