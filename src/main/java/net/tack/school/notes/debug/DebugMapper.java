package net.tack.school.notes.debug;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DebugMapper {

    //@Delete("DELETE LOW_PRIORITY FROM notes.folower")
    @Delete("DELETE LOW_PRIORITY FROM notes.user")
    //@Delete("DROP TABLE IF EXISTS notes.user, notes.note")
//    @Delete("BEGIN;\n" +
//            "DELETE LOW_PRIORITY FROM notes.user;\n" +
//            "DELETE LOW_PRIORITY FROM notes.user;")

    void clearUser();

    @Delete("DELETE LOW_PRIORITY FROM notes.usersession")
    void clearSession();

    @Delete("DELETE LOW_PRIORITY FROM notes.section")
    void clearSection();

    @Delete("DELETE LOW_PRIORITY FROM notes.note")
    void clearNote();

    @Delete("DELETE LOW_PRIORITY FROM notes.comment")
    void clearComment();

    @Insert("INSERT INTO `notes`.`user` (`login`, `pass`, `firstName`, `lastName`, `patronymic`, `status`) VALUES (\"admin\", \"123456\", \"sysadmin\", \"anykey\", \"notdevops\", \"SUPER\")")
    void insertAdmin();

    @Insert("INSERT INTO `notes`.`user` (`login`, `pass`, `rating`, `firstName`, `lastName`, `patronymic`) VALUES (\"Klein\", \"12345678\", 1, \"notsysadmin\", \"notanykey\", \"devops\")")
    void insertKlien();

}
