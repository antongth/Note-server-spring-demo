package net.tack.school.notes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.tack.school.notes.errorhandler.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
//@JsonRootName(value = "error")
public class ErrorResponse {
    private List<ErrorHandler.ServerError> errors = new ArrayList<>();

    public void add(ErrorHandler.ServerError error) {
        errors.add(error);
    }
}
