package net.tack.school.notes.model;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private int id;
    private String subject;
    private String body;
    private int sectionId;
    private int authorId;
    private Date created;
    private int revisionId;
    private int rating;

    private List<Revision> revisions;
    //private Map<Integer, String> RevisionToBody;
    //private List<Comment> comments;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return getId() == note.getId() && getSectionId() == note.getSectionId() && getAuthorId() == note.getAuthorId() && getRevisionId() == note.getRevisionId() && getRating() == note.getRating() && Objects.equals(getSubject(), note.getSubject()) && Objects.equals(getBody(), note.getBody()) && Objects.equals(getCreated(), note.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubject(), getBody(), getSectionId(), getAuthorId(), getCreated(), getRevisionId(), getRating());
    }
}
