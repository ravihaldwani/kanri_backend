package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserDto;

public class UserUtils {
    public static UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

//    public static User mapDtoToUser(UserDto userDto) {
//        User user = UserEntity.of(null, null, userDto.getEmail(), null);
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        return user;
//    }
}
