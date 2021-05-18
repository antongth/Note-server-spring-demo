package net.tack.school.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.util.Date;


public enum CommentDTO {;
    private interface NoteId {      int getNoteId(); }
    private interface Id {          int getId(); }
    private interface Body {        String getBody();}
    private interface RevisionId {  int getRevisionId(); }
    private interface AuthorId {    int getAuthorId(); }
    private interface Created {     Date getCreated(); }
    private interface Rating {      int getRating(); }

    public enum Request {;
        @Value public static class CreateComment implements NoteId, Body {
            int noteId;
            String body;
        }

        @Value public static class EditeComment implements Body {
            String body;
        }
    }

    public enum Response {;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Value public static class Comment implements Id, Body, NoteId, AuthorId, RevisionId, Created {
            int id;
            int noteId;
            String body;
            int revisionId;
            int authorId;
            Date created;
        }
    }
}
