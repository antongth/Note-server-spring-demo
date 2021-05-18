package net.tack.school.notes.dao;

import net.tack.school.notes.model.Comment;
import net.tack.school.notes.model.Note;
import net.tack.school.notes.model.User;

import java.util.List;

public interface CommentDao {

    int insert(User user, Comment comment);

    int update(Comment comment);

    int delete(Comment comment);

    int deleteAllFromNote(Note note);

    Comment getCommentById(int cid);

    List<Comment> getCommentByNote(Note note);

}
