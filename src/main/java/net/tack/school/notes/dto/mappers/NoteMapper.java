package net.tack.school.notes.dto.mappers;

import net.tack.school.notes.dto.NoteDTO;
import net.tack.school.notes.model.Note;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = RevisionMapper.class)
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    NoteDTO.Response.Note noteToNoteDto(Note note);

    @InheritInverseConfiguration
    Note noteDtoToNote(NoteDTO.Request.Note noteDto);

    @InheritInverseConfiguration
    Note noteDtoToNote(NoteDTO.Request.EditeNote noteDto);
}
