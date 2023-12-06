package com.example.demo.controller.user;

import com.example.demo.Service.UserService;
import com.example.demo.exception.EntityDuplicateException;
import com.example.demo.exception.NewEntityException;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.transfer.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SigninController {

    private UserService userService;

    @Autowired
    public SigninController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public Result signin(@RequestBody SigninRequestPackage signinRequestPackage){
        String username = signinRequestPackage.getUsername();
        String password = signinRequestPackage.getPassword();
        UserDTO registerUser = new UserDTO(username, password);
        try{
            userService.signin(registerUser);
            return ResultFactory.buildSuccessResult(null);
        } catch(EntityDuplicateException entityDuplicateException){
            return ResultFactory.buildFailResult(null, entityDuplicateException.getMessage());
        } catch(NewEntityException newEntityException){
            return ResultFactory.buildInternalServerErrorResult(null);
        }
    }
}

class SigninRequestPackage{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
