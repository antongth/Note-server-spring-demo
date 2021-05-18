package net.tack.school.notes.dto.mappers;

import net.tack.school.notes.dto.SectionDTO;
import net.tack.school.notes.model.Section;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectionMapper {
    SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);

    SectionDTO.Response.Section sectionToSectionDto(Section section);

    @InheritInverseConfiguration
    Section sectionDtoToSection(SectionDTO.Request.Section sectionDto);
}
