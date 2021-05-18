package net.tack.school.notes.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.dto.mappers.NoteMapper;
import net.tack.school.notes.service.NoteService;
import net.tack.school.notes.service.UserService;
import net.tack.school.notes.dto.CommentDTO;
import net.tack.school.notes.dto.NoteDTO;
import net.tack.school.notes.dto.mappers.CommentMapper;
import net.tack.school.notes.dto.SuccessResponse;
import net.tack.school.notes.model.Comment;
import net.tack.school.notes.model.Note;
import net.tack.school.notes.model.User;
import net.tack.school.notes.service.CommentService;
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
public class NoteCommentEndPoints {
    @NonNull
    private final UserService userService;
    @NonNull
    private final NoteService noteService;
    @NonNull
    private final CommentService commentService;

    @PostMapping(path = "/notes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public NoteDTO.Response.Note createNote(@CookieValue(value = "JAVASESSIONID") String sessionId,
                              @RequestBody @Valid NoteDTO.Request.Note noteDto) {
        User user = userService.getUserBySessionId(sessionId);
        Note note = NoteMapper.INSTANCE.noteDtoToNote(noteDto);
        return NoteMapper.INSTANCE.noteToNoteDto(noteService.createNote(user, note));
    }

    @GetMapping(path = "/notes/{nid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteDTO.Response.Note infoOnNote(@CookieValue(value = "JAVASESSIONID") String sessionId,
                              @RequestParam Integer nid) {
        User user = userService.getUserBySessionId(sessionId);
        Note note = noteService.getNoteById(nid);
        return NoteMapper.INSTANCE.noteToNoteDto(note);
    }

    @PutMapping(path = "/notes/{nid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public NoteDTO.Response.Note editeNote(@CookieValue(value = "JAVASESSIONID") String sessionId,
                             @RequestBody @Valid NoteDTO.Request.EditeNote noteDto,
                             @RequestParam Integer nid) {
        User user = userService.getUserBySessionId(sessionId);
        Note note = NoteMapper.INSTANCE.noteDtoToNote(noteDto);
        note.setId(noteService.getNoteById(nid).getId());
        if (noteService.editNote(note) == 1)
            return NoteMapper.INSTANCE.noteToNoteDto(note);
        else throw
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "unknown", new Exception("no effect was made"));
    }

    @DeleteMapping(path = "/notes/{nid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse deleteNote(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                      @PathVariable(name = "nid") Integer nid) {
        User user = userService.getUserBySessionId(sessionId);
        Note note = noteService.getNoteById(nid);
        noteService.deleteNote(note);
        return new SuccessResponse();
    }

    @PostMapping(path = "/notes/{nid}/rating", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse rateNote(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                    @RequestBody @Valid NoteDTO.Request.RateNote noteDto,
                                    @RequestParam Integer nid) {
        User user = userService.getUserBySessionId(sessionId);
        Note note = noteService.getNoteById(nid);
        note.setRating(noteDto.getRating());
        noteService.rateNote(note);
        return new SuccessResponse();
    }

    @GetMapping(path = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO.Response.Note> getNotes(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                  @RequestParam(required = false) int                       sectionId,
                                  @RequestParam(required = false) String                    sortByRating,
                                  @RequestParam(required = false) String[]                  tags,
                                  @RequestParam(required = false) boolean                   alltags,
                                  @RequestParam(required = false) String                    timeFrom,
                                  @RequestParam(required = false) String                    timeTo,
                                  @RequestParam(required = false) int                       user,
                                  @RequestParam(required = false) String                    include,
                                  @RequestParam(required = false) boolean                   comments,
                                  @RequestParam(required = false) boolean                   allVersions,
                                  @RequestParam(required = false) boolean                   commentVersion,
                                  @RequestParam(required = false, defaultValue = "0") int   from,
                                  @RequestParam(required = false, defaultValue = "0") int   count) {
        User user0 = userService.getUserBySessionId(sessionId);
        List<Note> notes = noteService.getNotes(
                sectionId,
                sortByRating,
                tags,
                alltags,
                timeFrom,
                timeTo,
                user,
                include,
                comments,
                allVersions,
                commentVersion,
                from,
                count);
        List<NoteDTO.Response.Note> list = new ArrayList<>(notes.size());
        for (var n:notes) {
            list.add(NoteMapper.INSTANCE.noteToNoteDto(n));
        }
        return list;
    }

    @PostMapping(path = "/comments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDTO.Response.Comment createComment(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                                     @RequestBody @Valid CommentDTO.Request.CreateComment commentDto) {
        User user = userService.getUserBySessionId(sessionId);
        Comment comment = CommentMapper.INSTANCE.commentDtoToComment(commentDto);
        commentService.createComment(user, comment);
        return CommentMapper.INSTANCE.commentToCommentDto(comment);
    }

    @GetMapping(path = "/notes/{nid}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentDTO.Response.Comment> getCommentsByNote(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                                               @PathVariable(name = "nid") Integer nid) {
        User user = userService.getUserBySessionId(sessionId);
        Note note = noteService.getNoteById(nid);
        List<Comment> list = commentService.getCommentsByNote(note);
        List<CommentDTO.Response.Comment> listDTO = new ArrayList<>(list.size());
        for (var c : list) {
            listDTO.add(CommentMapper.INSTANCE.commentToCommentDto(c));
        }
        return listDTO;
    }

    @PutMapping(path = "/comments/{cid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDTO.Response.Comment editeComment(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                   @RequestBody @Valid CommentDTO.Request.EditeComment commentDto,
                                   @RequestParam Integer cid) {
        User user = userService.getUserBySessionId(sessionId);
        Comment comment = CommentMapper.INSTANCE.commentDtoToComment(commentDto);
        comment.setId(commentService.getCommentById(cid).getId());
        commentService.editeComment(comment);
        return CommentMapper.INSTANCE.commentToCommentDto(comment);
    }

    @DeleteMapping(path = "/comments/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse deleteComment(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                         @RequestParam Integer cid) {
        User user = userService.getUserBySessionId(sessionId);
        Comment comment = commentService.getCommentById(cid);
        commentService.deleteComment(comment);
        return new SuccessResponse();
    }

    @DeleteMapping(path = "/notes/{nid}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse deleteCommentsByNote(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                                @RequestParam Integer nid) {
        User user = userService.getUserBySessionId(sessionId);
        Note note = noteService.getNoteById(nid);
        commentService.deleteCommentsByNote(note);
        return new SuccessResponse();
    }

}
