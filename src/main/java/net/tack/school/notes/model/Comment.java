package net.tack.school.notes.model;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private int noteId;
    private String body;
    private int revisionId;
    private int authorId;
    private Date created;
    private int rating;

}
