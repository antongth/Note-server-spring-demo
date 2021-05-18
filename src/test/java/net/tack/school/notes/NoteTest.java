package net.tack.school.notes;

import net.tack.school.notes.model.Comment;
import net.tack.school.notes.model.Note;
import net.tack.school.notes.model.Section;
import net.tack.school.notes.model.User;
import net.tack.school.notes.model.params.UserState;
import net.tack.school.notes.model.params.UserStatus;
import net.tack.school.notes.service.NoteService;
import net.tack.school.notes.service.UserService;
import net.tack.school.notes.debug.DebugDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class NoteTest {
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private DebugDaoImpl debugDao;

    private User klein;
    private String kleinToken;
    private User admin;
    private Section section;
    private Note note;
    private Comment comment;

    @BeforeEach
    public void setUp(){
        debugDao.clear();
        debugDao.ins();

        //kleinToken = userService.login(klein);

        admin = new User();
        admin.setLogin("admin");
        admin.setPass("123456");
        admin.setLastName("anykey");
        admin.setFirstName("sysadmin");
        admin.setPatronymic("notdevops");
        admin.setStatus(UserStatus.SUPER);
        admin.setState(UserState.ACTIVE);
        admin.setRating(0);

        section = new Section();
        section.setName("Politics");

        note = new Note();
        note.setSubject("Politics");
        note.setBody("Go to Meetings!");

        comment = new Comment();
        comment.setBody("Navaliny is a lie");
    }

    @BeforeEach
    public void clear(){
        debugDao.clear();
    }

    @Test
    public void testCreateSection(){
        Assertions.assertNotEquals(section.getId(), noteService.createSection(klein, section).getId());
    }

    @Test
    void testGetSectionById() {
        Section sectionFromDb = noteService.createSection(klein, section);
        Assertions.assertEquals(section.getName(), noteService.getSectionById(sectionFromDb.getId()).getName());
    }

    @Test
    void testGetSections() {
        assertEquals(1, noteService.getSections().size());
    }

    @Test
    void testRenameSection() {
        Section sectionFromDb = noteService.createSection(klein, section);
        sectionFromDb.setName("USA problems");
        noteService.renameSection(sectionFromDb);
        Assertions.assertEquals("USA problems", noteService.getSectionById(sectionFromDb.getId()).getName());
    }

    @Test
    void testDeleteSection() {
        Section sectionFromDb = noteService.createSection(klein, section);
        noteService.deleteSection(sectionFromDb.getId());
        assertEquals(1, noteService.deleteSection(sectionFromDb.getId()));
    }

    @Test
    void testCreateNote() {
    }

    @Test
    void testGetNoteById() {
    }

    @Test
    void testEditNote() {
    }

    @Test
    void testDeleteNote() {
    }

    @Test
    void testRateNote() {
    }

    @Test
    void testGetNotes() {
    }
}
