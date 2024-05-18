package com.example.CRSpringBoot.Service.impl;

import com.example.CRSpringBoot.Dto.LoginDto;
import com.example.CRSpringBoot.Dto.UpdateDto;
import com.example.CRSpringBoot.Dto.UserDto;
import com.example.CRSpringBoot.Entity.ApiResponse;
import com.example.CRSpringBoot.Entity.User;
import com.example.CRSpringBoot.Repo.ApiResponseRepository;
import com.example.CRSpringBoot.Repo.UserRepo;
import com.example.CRSpringBoot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserIMPL implements UserService {

    @Autowired(required = false)
    private UserRepo userRepo;

    @Autowired(required = false)
    private ApiResponseRepository apiResponseRepository;

    private static final String API_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETH,LTC";


    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDto userid) {
        User user = new User(
                userid.getId(),
                userid.getUserName(),
                userid.getEmail(),
                this.passwordEncoder.encode(userid.getPassword()),
                userid.getNumber(),
                userid.getFirstName(),
                userid.getLastName()
        );
        userRepo.save(user);
        return user.getUserName();
    }

    @Override
    public String LoginUser(LoginDto loginDto) {
        User user = userRepo.findByEmail(loginDto.getEmail());
        if (user != null) {
            String password = loginDto.getPassword();
            String encodedPassword = user.getPassword();
            boolean iSpasswordRight = passwordEncoder.matches(password, encodedPassword);
            if (!iSpasswordRight) {
                return "Password not matching";
            } else {
                Optional<User> users = userRepo.findOneByEmailAndPassword(loginDto.getEmail(), encodedPassword);
                if (users.isPresent()) {
                    return "Login Success";
                } else {
                    return "Login Failed";
                }
            }
        }
        return "Email not exist";
    }

    @Override
    public String updateUser(UpdateDto userDto) {
        User user = userRepo.findByEmail(userDto.getEmail());
        if (user != null) {
            user.setUserName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setNumber(userDto.getNumber());
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            userRepo.save(user);
            return "User updated successfully";
        }
        return "User not found";
    }

    public void callAndStoreApi(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.GET, null, String.class);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setUserId(userId);
        apiResponse.setResponse(responseEntity.getBody());
        apiResponse.setTimestamp(LocalDateTime.now());

        apiResponseRepository.save(apiResponse);
    }
}
