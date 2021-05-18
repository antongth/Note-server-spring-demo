package net.tack.school.notes.service;

import net.tack.school.notes.model.UserSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserSessionService {

    private static Map<String, UserSession> userSessionMap = new HashMap<>();

    public static synchronized void updateTimeOfAction(String login) {
        UserSession userSession = userSessionMap.get(login);
        if (userSession != null)
                userSession.setLastAction(new Date());
    }

    public static synchronized void removeSession(String login) {
        userSessionMap.remove(login);
    }

    public static synchronized void putSession(String login, UserSession session) {
        userSessionMap.put(login, session);
    }
}