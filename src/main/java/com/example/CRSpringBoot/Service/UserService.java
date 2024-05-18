package com.example.CRSpringBoot.Service;

import com.example.CRSpringBoot.Dto.LoginDto;
import com.example.CRSpringBoot.Dto.UpdateDto;
import com.example.CRSpringBoot.Dto.UserDto;

public interface UserService {

    String addUser(UserDto userid);

    String LoginUser(LoginDto loginDto);

    String updateUser(UpdateDto userDto);

    void callAndStoreApi(String userId);
}
