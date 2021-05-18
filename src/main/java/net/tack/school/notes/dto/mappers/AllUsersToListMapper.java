package net.tack.school.notes.dto.mappers;

import net.tack.school.notes.model.params.UserState;
import net.tack.school.notes.model.params.UserStatus;
import net.tack.school.notes.dto.UserDTO;
import net.tack.school.notes.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AllUsersToListMapper {

    AllUsersToListMapper INSTANCE = Mappers.getMapper(AllUsersToListMapper.class);

    @Mapping(ignore = true, target = "online", source = "sessionId")
    @Mapping(ignore = true, target = "password", source = "pass")
    @Mapping(ignore = true, target = "superUser")
    @Mapping(ignore = true, target = "deleted", source = "state")
    UserDTO.Response.AllUsersToList userToUserDto(User user);

    @InheritInverseConfiguration
    User userDtoToUser(UserDTO.Response.AllUsersToList userDto);

    default boolean srtToBoolean(String s) {
        return s != null;
    }

    default String booleanToSrt(boolean b) {
        return b ? "sessionId is not read from d" : null;
    }

    default boolean userStatusToBoolean(UserStatus status) {
        return status == UserStatus.SUPER;
    }

    default UserStatus booleanToUserStatus(boolean superUser) {
        return superUser ? UserStatus.SUPER : UserStatus.USER;
    }

    default boolean userStateToBoolean(UserState state) {
        return state == UserState.DELETED;
    }

    default UserState booleanToUserState(boolean deleted) {
        return deleted ? UserState.DELETED : UserState.RESTORED;
    }

}
