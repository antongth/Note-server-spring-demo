package net.tack.school.notes.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.service.NoteService;
import net.tack.school.notes.service.UserService;
import net.tack.school.notes.dto.SectionDTO;
import net.tack.school.notes.dto.mappers.SectionMapper;
import net.tack.school.notes.dto.SuccessResponse;
import net.tack.school.notes.model.Section;
import net.tack.school.notes.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SectionEndPoints {
    @NonNull
    private final UserService userService;
    @NonNull
    private final NoteService noteService;

    @PostMapping(path = "/sections", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SectionDTO.Response.Section createSection(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                    @RequestBody @Valid SectionDTO.Request.Section sectionDto) {
        User user = userService.getUserBySessionId(sessionId);
        Section section = SectionMapper.INSTANCE.sectionDtoToSection(sectionDto);
        noteService.createSection(user, section);
        return SectionMapper.INSTANCE.sectionToSectionDto(section);
    }

    @PutMapping(path = "/sections/{sid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SectionDTO.Response.Section renameSection(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                    @RequestBody @Valid SectionDTO.Request.Section sectionDto,
                                    @PathVariable(name = "sid") Integer sid) {
        User user = userService.getUserBySessionId(sessionId);
        Section section = noteService.getSectionById(sid);
        section.setName(sectionDto.getName());
        if (noteService.renameSection(section) == 1)
            return SectionMapper.INSTANCE.sectionToSectionDto(section);
        else throw
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "name", new Exception("no effect was made"));
    }

    @DeleteMapping(path = "/sections/{sid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse deleteSection(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                         @PathVariable(name = "sid") Integer sid) {
        User user = userService.getUserBySessionId(sessionId);
        Section section = noteService.getSectionById(sid);
        if (noteService.deleteSection(sid) == 1)
            return new SuccessResponse();
        else throw
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "sid", new Exception("no effect was made"));
    }

    @GetMapping(path = "/sections/{sid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SectionDTO.Response.Section infoOnSection(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                    @PathVariable(name = "sid") Integer sid) {
        User user = userService.getUserBySessionId(sessionId);
        Section section = noteService.getSectionById(sid);
        return SectionMapper.INSTANCE.sectionToSectionDto(section);
    }

    @GetMapping(path = "/sections", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SectionDTO.Response.Section> getSections(@CookieValue(value = "JAVASESSIONID") String sessionId) {

        User user = userService.getUserBySessionId(sessionId);
        List<Section> sections = noteService.getSections();
        List<SectionDTO.Response.Section> list = new ArrayList<>(sections.size());
        for (var s : sections) {
            list.add(SectionMapper.INSTANCE.sectionToSectionDto(s));
        }
        return list;
    }
}