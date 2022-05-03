package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import org.hibernate.Session;

public class UserController {
    private Session session;

    public UserController(Session session) {
        this.session = session;
    }

    public void addUser(User user) throws Exception {
        session.save(user);
        session.flush();
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
