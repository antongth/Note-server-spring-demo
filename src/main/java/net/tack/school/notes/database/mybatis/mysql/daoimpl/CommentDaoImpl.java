package net.tack.school.notes.database.mybatis.mysql.daoimpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.model.Comment;
import net.tack.school.notes.model.Note;
import net.tack.school.notes.dao.CommentDao;
import net.tack.school.notes.database.mybatis.mysql.mappers.CommentMapper;
import net.tack.school.notes.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentDaoImpl implements CommentDao {
    @NonNull
    private final CommentMapper commentMapper;


    @Override
    public int insert(User user, Comment comment) {
        return commentMapper.insert(user, comment);
    }

    @Override
    public int update(Comment comment) {
        return commentMapper.update(comment);

    }

    @Override
    public int delete(Comment comment) {
        return commentMapper.delete(comment);
    }

    @Override
    public Comment getCommentById(int cid) {
        return Optional.ofNullable(commentMapper.get(cid)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "cid", new Exception("cid is not exist")));
    }

    @Override
    public List<Comment> getCommentByNote(Note note) {
        return Optional.ofNullable(commentMapper.getAllByNote(note)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "nid", new Exception("cid is not exist")));
    }

    @Override
    public int deleteAllFromNote(Note note) {
        return commentMapper.deleteAllFromNote(note);
    }
}
