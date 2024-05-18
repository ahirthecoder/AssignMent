package com.example.CRSpringBoot.Controller;

import com.example.CRSpringBoot.Dto.LoginDto;
import com.example.CRSpringBoot.Dto.UpdateDto;
import com.example.CRSpringBoot.Dto.UserDto;
import com.example.CRSpringBoot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserController {

    @Autowired(required = false)
    public UserService userService;
    private static final String API_KEY = "27ab17d1-215f-49e5-9ca4-afd48810c149";

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestHeader(value = "X-CMC_PRO_API_KEY") String apiKey, @RequestBody UserDto userDto) {
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.ok("Invalid API key");
        }
        String name = userService.addUser(userDto);
        return ResponseEntity.ok(name);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestHeader(value = "X-CMC_PRO_API_KEY") String apiKey, @RequestBody LoginDto loginDto) {
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.ok("Invalid API key");
        }
        String loginResponse = userService.LoginUser(loginDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateDto updateDto) {
        String updateResponse = userService.updateUser(updateDto);
        return ResponseEntity.ok(updateResponse);
    }

    @PostMapping("/callapi")
    public ResponseEntity<?> callApi(@RequestHeader(value = "X-CMC_PRO_API_KEY") String apiKey, @RequestBody UserDto userDto) {
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.ok("Invalid API key");
        }

        userService.callAndStoreApi(userDto.email);
        return ResponseEntity.ok("API call and storage successful");
    }
}
