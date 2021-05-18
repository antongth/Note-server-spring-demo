package net.tack.school.notes.debug;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.dto.SettingsResponse;
import net.tack.school.notes.dto.SuccessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/debug")
public class DebugEndPoints {

    @Value("${max_name_length}")
    private String maxNameLength;

    @Value("${min_password_length}")
    private String minPasswordLength;

    @Value("${user_idle_timeout}")
    private String userIdleTimeout;

    @NonNull
    private DebugDaoImpl debugDao;


    @PostMapping(path = "/clear")
    public SuccessResponse clear() {
        debugDao.clear();
        return new SuccessResponse();
    }

    @PostMapping(path = "/ins")
    public SuccessResponse ins() {
        debugDao.ins();
        return new SuccessResponse();
    }

    @GetMapping(path = "/settings")
    public SettingsResponse settings() {
        return new SettingsResponse(maxNameLength, minPasswordLength, userIdleTimeout);
    }
}
