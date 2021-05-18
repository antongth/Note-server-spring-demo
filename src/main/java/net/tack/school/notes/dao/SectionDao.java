package net.tack.school.notes.dao;

import net.tack.school.notes.model.Section;
import net.tack.school.notes.model.User;

import java.util.List;
import java.util.Optional;

public interface SectionDao {

    Section insert(User user, Section section);

    int update(Section section);

    int delete(int sid);

    Optional<Section> get(int sid);

    Optional<List<Section>> getAll();
}
