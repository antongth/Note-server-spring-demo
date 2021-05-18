package net.tack.school.notes.database.mybatis.mysql.mappers;

import net.tack.school.notes.model.Comment;
import net.tack.school.notes.model.Note;
import net.tack.school.notes.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO notes.comment (noteId, userId, revisionId, body, created) " +
            "VALUES (#{comment.noteId}, #{user.id}, #{comment.revisionId}, #{comment.body}, now()")
    @Options(useGeneratedKeys = true, keyProperty = "comment.id", keyColumn = "id")
    int insert(@Param("user") User user, @Param("comment") Comment comment);

    @Update("UPDATE notes.comment SET body = #{comment.body}")
    int update(@Param("comment") Comment comment);

    @Delete("DELETE FROM notes.comment WHERE id = #{comment.id}")
    int delete(@Param("comment") Comment comment);

    @Delete("DELETE * FROM notes.comment WHERE noteId = #{note.id}")
    int deleteAllFromNote(Note note);

    @Select("SELECT * FROM notes.comment WHERE id = #{cid}")
    Comment get(int cid);

    @Select("SELECT * FROM notes.comment WHERE NoteId = #{note.id}")
    List<Comment> getAllByNote(Note note);
}
