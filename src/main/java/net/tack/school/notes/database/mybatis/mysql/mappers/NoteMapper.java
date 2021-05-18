package net.tack.school.notes.database.mybatis.mysql.mappers;

import net.tack.school.notes.model.Note;
import net.tack.school.notes.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO notes.note (userId, sectionId, subject) " +
            "VALUES (#{user.id}, #{note.sectionId}, #{note.subject})")
    @Options(useGeneratedKeys = true, keyProperty = "note.id", keyColumn = "id")
    int insert(@Param("user") User user, @Param("note") Note note);

    @Insert("INSERT INTO notes.revision (noteId, sectionId, body, created) " +
            "VALUES (#{note.id}, #{note.sectionId}, #{note.body}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "note.revisionId", keyColumn = "id")
    int insertRevision(Note note);

    @Update("UPDATE notes.note " +
            "SET revisionId = #{note.revisionId}, sectionId = #{note.sectionId}, subject = #{note.subject}, rating = #{note.rating} " +
            "WHERE id = #{note.id}")
    int update(Note note);

    @Delete("DELETE * FROM notes.note WHERE id = #{note.id}")
    int delete(Note note);

    @Delete("DELETE FROM notes.note WHERE userId = #{user.id}")
    int deleteAllByUser(User user);

    @Select("SELECT * FROM notes.note")
    List<Note> getAll();

    @Select("SELECT * FROM notes.note WHERE id = #{nid}")
    Note get(int nid);

    @Select("SELECT * FROM notes.note WHERE userId = #{user.id}")
    List<Note> getAllByUser(User user);
}
