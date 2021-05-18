package net.tack.school.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteDTO {
    private interface Id {          int getId(); }
    private interface Body {        String getBody(); }
    private interface Subject {     String getSubject(); }
    private interface SectionId {   int getSectionId(); }
    private interface AuthorId {    int getAuthorId(); }
    private interface Created {     Date getCreated(); }
    private interface RevisionId {  int getRevisionId(); }
    private interface Rating {      int getRating(); }
    private interface Revisions {   List<RevisionDTO.Response.Revision> getRevisions(); }

    public enum Request {;
        @Value
        public static class Note implements Subject, Body, SectionId {
            String subject;
            String body;
            int sectionId;
        }

        @Value
        public static class EditeNote implements Body, SectionId {
            String body;
            int sectionId;
        }

        @Value
        public static class RateNote implements Rating {
            int rating;
        }
    }

    public enum Response {;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Value
        public static class Note implements Id, Subject, Body, SectionId, AuthorId, Created, RevisionId {
            int id;
            String subject;
            String body;
            int sectionId;
            int authorId;
            Date created;
            int revisionId;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Value
        public static class GetAllNote implements Id, Subject, Body, SectionId, AuthorId, Created, RevisionId, Revisions {
            int id;
            String subject;
            String body;
            int sectionId;
            int authorId;
            Date created;
            int revisionId;
            List<RevisionDTO.Response.Revision> revisions;
        }
    }
}
