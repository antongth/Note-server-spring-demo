package net.tack.school.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum SectionDTO {;
    private interface Id { int getId(); }

    private interface Name {@NotBlank
                            @Pattern(regexp = "[A-Za-zА-Яа-я0-9_ ]*", message = "must be char or num")
                            String getName(); }

    public enum Request {;
        @Value
        public static class Section implements Name {
            String name;
        }
    }
    public enum Response {;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Value
        public static class Section implements Id, Name{
            int id;
            String name;
        }
    }
}
