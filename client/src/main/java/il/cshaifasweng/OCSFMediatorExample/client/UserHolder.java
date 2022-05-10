package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;

public class UserHolder {
    private static User user;
    private static int id;

    public static void setUser(User newUser) {
        user = newUser;
    }

    public User getUser() {
        return user;
    }

    public static void setID(int ID) {
        id = ID;
    }

    public int getID() {
        return id;
    }
}
