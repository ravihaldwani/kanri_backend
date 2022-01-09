package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserDto;

public class UserUtils {
    public static UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setStatus(user.getStatus());
        userDto.setCreatedDate(user.getCreatedDate());
        return userDto;
    }

//    public static User mapDtoToUser(UserDto userDto) {
//        User user = UserEntity.of(null, null, userDto.getEmail(), null);
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        return user;
//    }
}
