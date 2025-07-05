package com.spring.auth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.auth.Module.userModule;
import com.spring.auth.Service.userService;
import com.spring.auth.Token.jwtToken;
import com.spring.auth.custom.CustomUserDetails;

@RestController
@RequestMapping("/public")
public class userController {
    
    @Autowired userService userService;
    @Autowired jwtToken jwtToken;
    @Autowired AuthenticationManager manager;
    @Autowired CustomUserDetails customUserDetails;

    @PostMapping("/reg")
    public ResponseEntity<?> Register(@RequestBody userModule userModule){
        try {
            userModule ud = userService.RegisterService(userModule);
            if (ud != null) {
                userService.sendMail(ud.getEmail(), ud.getEmail());
                return ResponseEntity.ok().body("success");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error : "+ e.getMessage());
        }
        return ResponseEntity.badRequest().body("failed !");
    }

    @PostMapping("/log")
    public ResponseEntity<?> Login(@RequestBody userModule userModule){
        try {
            userModule ud = userService.LoginService(userModule);
            if (ud != null) {
                Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(userModule.getEmail(), userModule.getPass())
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String Token = jwtToken.TokenGenrate(ud.getEmail());
                return ResponseEntity.ok().body(Token);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error : "+ e.getMessage());
        }
        return ResponseEntity.badRequest().body("failed !");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyuser(@RequestParam int code){
        try {
            userModule ud = userService.verifyService(code);
            if (ud != null) {
                String Token = jwtToken.TokenGenrate(ud.getEmail());
                return ResponseEntity.ok().body(Token);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error : "+ e.getMessage());
        }
        return ResponseEntity.badRequest().body("failed !");
    }
}
