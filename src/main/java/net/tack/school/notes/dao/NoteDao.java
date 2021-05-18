package net.tack.school.notes.dao;

import net.tack.school.notes.model.Note;
import net.tack.school.notes.model.User;

import java.util.List;
import java.util.Optional;

public interface NoteDao {

    Note insert(User user, Note note);

    Note insertRevision(Note note);

    int update(Note note);

    int changeSection(Note note);

    int rate(Note note);

    int delete(Note note);

    int deleteAllByUser(User user);

    Optional<Note> get(int nid);

    List<Note> getAll();

    List<Note> getNotesByUser(User user);


}
