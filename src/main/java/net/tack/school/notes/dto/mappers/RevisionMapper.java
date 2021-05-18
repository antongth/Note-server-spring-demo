package net.tack.school.notes.dto.mappers;

import net.tack.school.notes.dto.RevisionDTO;
import net.tack.school.notes.model.Revision;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CommentMapper.class)
public interface RevisionMapper {
    RevisionMapper INSTANCE = Mappers.getMapper(RevisionMapper.class);

    RevisionDTO revisionToRevisionDto(Revision revision);

    @InheritInverseConfiguration
    Revision revisionDtoToRevision(RevisionDTO revisionDto);
}
