package com.example.demo.controller.user;

import com.example.demo.Service.UserService;
import com.example.demo.exception.FieldMismatchException;
import com.example.demo.exception.NoEntityInDatabaseException;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginPackage loginPackage){
        String username = loginPackage.getUsername();
        String password = loginPackage.getPassword();
        try{
            User user = this.userService.login(username, password);
            return ResultFactory.buildSuccessResult(user);
        } catch(NoEntityInDatabaseException | FieldMismatchException e){
            return ResultFactory.buildFailResult(null, e.getMessage());
        }
    }

}

class LoginPackage{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
