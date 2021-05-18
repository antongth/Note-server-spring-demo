package net.tack.school.notes;

import net.tack.school.notes.database.mybatis.mysql.mappers.UserMapper;
import net.tack.school.notes.model.User;
import net.tack.school.notes.model.params.UserState;
import net.tack.school.notes.model.params.UserStatus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;

public class TestMybatis {
    private User klein;
    private User admin;

    @BeforeEach
    public void setUp() {
        klein = new User();
        klein.setLogin("Klein");
        klein.setPass("12345678");
        klein.setLastName("notanykey");
        klein.setFirstName("notsysadmin");
        klein.setPatronymic("devops");
        klein.setStatus(UserStatus.USER);
        klein.setState(UserState.ACTIVE);
        klein.setRating(1);

        admin = new User();
        admin.setLogin("admin");
        admin.setPass("123456");
        admin.setLastName("anykey");
        admin.setFirstName("sysadmin");
        admin.setPatronymic("notdevops");
        admin.setStatus(UserStatus.SUPER);
        admin.setState(UserState.ACTIVE);
        admin.setRating(0);
    }

    @AfterEach
    public void clear() {
    }

    @Test
    void testGetUser() {
        SqlSessionFactory sessionFactory;
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")){
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);

//            Configuration configuration = sessionFactory.getConfiguration();
//            Set<String> methods = configuration.getLazyLoadTriggerMethods();
//            methods.clear();
//            methods.remove("hashCode");
//            methods.remove("toString");

            SqlSession sqlSession = sessionFactory.openSession();
            User kleinFromDb = sqlSession.getMapper(UserMapper.class).getUserByLogin(klein.getLogin());
            User adminFromDb = sqlSession.getMapper(UserMapper.class).getUserByLogin(admin.getLogin());
            sqlSession.getMapper(UserMapper.class).setUserFollowingForUser(adminFromDb, kleinFromDb);
            System.out.println("!");
            sqlSession.getMapper(UserMapper.class).setUserFollowingForUser(adminFromDb, kleinFromDb);
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
