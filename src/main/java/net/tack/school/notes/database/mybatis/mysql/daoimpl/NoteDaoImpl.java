package net.tack.school.notes.database.mybatis.mysql.daoimpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.dao.NoteDao;
import net.tack.school.notes.database.mybatis.mysql.mappers.NoteMapper;
import net.tack.school.notes.model.Note;
import net.tack.school.notes.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NoteDaoImpl implements NoteDao {
    @NonNull
    private final NoteMapper noteMapper;

    @Override
    public Note insert(User user, Note note) {
        noteMapper.insert(user, note);
        noteMapper.insertRevision(note);
        return note;
    }

    @Override
    public Note insertRevision(Note note) {
        noteMapper.insertRevision(note);
        return note;
    }

    @Override
    public int update(Note note) {
        noteMapper.insertRevision(note);
        return noteMapper.update(note);
    }

    @Override
    public int changeSection(Note note) {
        return noteMapper.update(note);
    }

    @Override
    public int rate(Note note) {
       return noteMapper.update(note);
    }

    @Override
    public int delete(Note note) {
        return noteMapper.delete(note);
    }

    @Override
    public int deleteAllByUser(User user) {
        return noteMapper.deleteAllByUser(user);
    }

    @Override
    public Optional<Note> get(int nid) {
        return Optional.ofNullable(noteMapper.get(nid));
    }

    @Override
    public List<Note> getAll() {
        return noteMapper.getAll();
    }

    @Override
    public List<Note> getNotesByUser(User user) {
        return noteMapper.getAllByUser(user);
    }
}
