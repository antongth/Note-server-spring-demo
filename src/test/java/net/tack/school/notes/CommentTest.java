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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CommentTest {
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
    void testGetCommentById() {
    }

    @Test
    void testCreateComment() {
    }

    @Test
    void testGetCommentsByNote() {
    }

    @Test
    void testEditeComment() {
    }

    @Test
    void testDeleteComment() {
    }

    @Test
    void testDeleteCommentsByNote() {
    }
}