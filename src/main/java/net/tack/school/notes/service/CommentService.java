package net.tack.school.notes.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.model.Comment;
import net.tack.school.notes.model.Note;
import net.tack.school.notes.database.mybatis.mysql.daoimpl.CommentDaoImpl;
import net.tack.school.notes.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    @NonNull
    private CommentDaoImpl commentDao;

    public Comment getCommentById(Integer cid) {
        return commentDao.getCommentById(cid);
    }

    public void createComment(User user, Comment comment) {
        commentDao.insert(user, comment);
    }

    public List<Comment> getCommentsByNote(Note note) {
        return commentDao.getCommentByNote(note);
    }

    public int editeComment(Comment comment) {
        return commentDao.update(comment);
    }

    public int deleteComment(Comment comment) {
        return commentDao.delete(comment);
    }

    public int deleteCommentsByNote(Note note) {
        return commentDao.deleteAllFromNote(note);
    }
}
