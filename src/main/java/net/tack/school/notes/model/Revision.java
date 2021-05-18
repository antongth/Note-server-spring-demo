package net.tack.school.notes.model;

import lombok.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Revision {
    private int id;
    private String body;
    private Date created;
    private Set<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Revision)) return false;
        Revision revision = (Revision) o;
        return getId() == revision.getId() && Objects.equals(getBody(), revision.getBody()) && Objects.equals(getCreated(), revision.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBody(), getCreated());
    }
}
