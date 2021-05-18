package net.tack.school.notes.model;

import lombok.Getter;
import lombok.Setter;
import net.tack.school.notes.service.UserService;

import java.util.Date;

//@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserSession extends Thread{
    private UserService userService;
    @Getter
    private User user;
    private int idleTime;
    @Setter
    private volatile Date lastAction;

    public UserSession(UserService userService, int idleTime, User user, Date lastAction) {
        this.userService = userService;
        this.idleTime = idleTime;
        this.user = user;
        this.lastAction = lastAction;
    }

    @Override
    public void run() {
        try {
            while (true) {
                long waitTime = new Date().getTime() - lastAction.getTime();
                sleep(1000*idleTime - waitTime);
                if (new Date().getTime() - lastAction.getTime() >= 1000*idleTime) {
                    userService.logout(user);
                    break;
                }
            }
            System.out.println("session is out");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
