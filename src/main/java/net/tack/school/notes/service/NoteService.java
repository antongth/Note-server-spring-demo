package net.tack.school.notes.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.database.mybatis.mysql.daoimpl.NoteDaoImpl;
import net.tack.school.notes.database.mybatis.mysql.daoimpl.SectionDaoImpl;
import net.tack.school.notes.model.Note;
import net.tack.school.notes.model.Section;
import net.tack.school.notes.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteService {
    @NonNull
    private NoteDaoImpl noteDao;
    @NonNull
    private SectionDaoImpl sectionDao;


    public Section createSection(User user, Section section) {
        sectionDao.insert(user, section);
        return section;
    }

    public Section getSectionById(int sid) {
        return sectionDao.get(sid).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "sid", new Exception("sid is not exist")));
    }

    public List<Section> getSections() {
        return sectionDao.getAll().orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "", new Exception("no sections")));
    }

    public int renameSection(Section section){
        return sectionDao.update(section);
    }

    public int deleteSection(int sid) {
        return sectionDao.delete(sid);
    }


    public Note createNote(User user, Note note) {
        noteDao.insert(user, note);
        return note;
    }

    public Note getNoteById(Integer nid) {
        return noteDao.get(nid).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "nid", new Exception("no note with nid")));
    }

    public int editNote(Note note) {
        return noteDao.update(note);
    }

    public int deleteNote(Note note) {
        return noteDao.delete(note);
    }

    public int rateNote(Note note) {
        return noteDao.rate(note);
    }

    public List<Note> getNotes(int sectionId, String sortByRating, String[] tags, boolean alltags, String timeFrom, String timeTo, int user, String include, boolean comments, boolean allVersions, boolean commentVersion, int from, int count) {
        return null;
    }
}
