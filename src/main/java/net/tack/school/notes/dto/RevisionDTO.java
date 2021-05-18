package net.tack.school.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.util.Date;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RevisionDTO {
    private interface Id {          int getId(); }
    private interface Body {        String getBody();}
    private interface Created {     Date getCreated(); }
    private interface Comments {    List<CommentDTO.Response.Comment> getComments(); }

    public enum Request {;
        @Value
        public static class Revision {;}
    }

    public enum Response {;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Value
        public static class Revision {;}
    }
}