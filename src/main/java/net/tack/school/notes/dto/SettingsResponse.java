package net.tack.school.notes.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@RequiredArgsConstructor
public class SettingsResponse {
    @NonNull
    @Value("${max_name_length}")
    private String maxNameLength;
    @NonNull
    @Value("${min_password_length}")
    private String minPasswordLength;
    @NonNull
    @Value("${user_idle_timeout}")
    private String userIdleTimeout;
}
