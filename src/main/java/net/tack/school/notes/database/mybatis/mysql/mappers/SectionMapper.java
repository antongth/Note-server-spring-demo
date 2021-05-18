package net.tack.school.notes.database.mybatis.mysql.mappers;

import net.tack.school.notes.model.Section;
import net.tack.school.notes.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SectionMapper {

    @Insert("INSERT INTO notes.section (userId, name) VALUES (#{user.id}, #{section.name}) ")
    @Options(useGeneratedKeys = true, keyProperty = "section.id", keyColumn = "id")
    int insert(@Param("user") User user, @Param("section") Section section);

    @Update("UPDATE notes.section SET name=#{section.name} WHERE id=#{section.id}")
    int update(@Param("section") Section section);

    @Delete("DELETE FROM notes.section WHERE id=#{sid}")
    int delete(int sid);

    @Select("SELECT * FROM notes.section WHERE id=#{sid}")
    Section get(int sid);

    @Select("SELECT * FROM notes.section")
    List<Section> getAll();
}
