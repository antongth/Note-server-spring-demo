package net.tack.school.notes.debug;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DebugDaoImpl implements DebugDao {
    @NonNull
    private DebugMapper debugMapper;

    @Override
    public void clear() {
        debugMapper.clearUser();
        debugMapper.clearSection();
        debugMapper.clearNote();
        debugMapper.clearComment();
        debugMapper.clearSession();

        debugMapper.insertAdmin();
    }

    @Override
    public void ins() {
        debugMapper.insertKlien();
    }

}
