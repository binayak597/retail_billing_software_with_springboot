package com.rbs.retail.billing.services;

import com.rbs.retail.billing.dto.UserDto;
import com.rbs.retail.billing.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserDto dto);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);
}
