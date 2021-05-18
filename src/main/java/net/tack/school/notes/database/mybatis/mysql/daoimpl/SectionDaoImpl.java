package net.tack.school.notes.database.mybatis.mysql.daoimpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.dao.SectionDao;
import net.tack.school.notes.database.mybatis.mysql.mappers.SectionMapper;
import net.tack.school.notes.model.Section;
import net.tack.school.notes.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class SectionDaoImpl implements SectionDao {
    @NonNull
    private final SectionMapper sectionMapper;


    @Override
    public Section insert(User user, Section section) {
        sectionMapper.insert(user, section);
        return section;
    }

    @Override
    public int update(Section section) {
        return sectionMapper.update(section);
    }

    @Override
    public int delete(int sid) {
        return sectionMapper.delete(sid);
    }

    @Override
    public Optional<Section> get(int sid) {
        return Optional.ofNullable(sectionMapper.get(sid));
    }

    @Override
    public Optional<List<Section>> getAll() {
        return Optional.ofNullable(sectionMapper.getAll());
    }
}
