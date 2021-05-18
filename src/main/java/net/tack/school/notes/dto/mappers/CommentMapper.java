package net.tack.school.notes.dto.mappers;

import net.tack.school.notes.model.Comment;
import net.tack.school.notes.dto.CommentDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO.Response.Comment commentToCommentDto(Comment comment);

    @InheritInverseConfiguration
    Comment commentDtoToComment(CommentDTO.Request.CreateComment commentDto);

    @InheritInverseConfiguration
    Comment commentDtoToComment(CommentDTO.Request.EditeComment commentDto);
}
