package com.spring.auth.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.auth.Exception.userException;
import com.spring.auth.Module.userModule;
import com.spring.auth.Repo.userRepo;
import com.spring.auth.interfaces.userServiceInterface;

@Service
public class userService implements userServiceInterface{

    @Autowired private userRepo userRepo;
    private userModule ud;
    @Autowired JavaMailSender javaMailSender;
    private int OTP;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public userModule RegisterService(userModule userModule) {
        userModule email = userRepo.findByEmail(userModule.getEmail());
        userModule name = userRepo.findByEmail(userModule.getName());
        if (email != null) {
            throw new userException("you already register with this email id");
        }else if(name != null){
            throw new userException("this user name already taken by another user");
        }else if(userModule.getName().length() < 3){
            throw new userException("user name length more than 3 chars");
        }else if(userModule.getPass().length() < 7){
            throw new userException("user password length more than 7 chars");
        }else if(email == null && name == null){
            ud = new userModule(null, userModule.getName(), userModule.getEmail(), passwordEncoder.encode(userModule.getPass()), new Date(), false);
            return ud;
        }
        return null;
    }

    @Override
    public userModule LoginService(userModule userModule) {
        userModule ue = userRepo.findByEmail(userModule.getEmail());
                                                  //raw password and encrypt password
        if (ue != null && passwordEncoder.matches(userModule.getPass(), ue.getPass()) ) {
            return ue;
        }else{
            throw new userException("user email id or password incorrect !");
        }
    }

    @Override
    public userModule verifyService(int code) {
        if (OTP == code) {
            ud.setisCheck(true);
            return userRepo.save(ud);
        }else{
            throw new userException("Invalid OTP Entered . . .");
        }
    }

    @Override
    public void sendMail(String email, String name) {
        OTP = generateOTP();
        int last = OTP;
        int val = 0;
        while (last > 0) {
            int rev = last % 10;
            val = rev;
            last /= 10;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP code is : " + val + "xxxxx");
        message.setText("Dear user [" + name + "]"+ "\n" + "Your OTP code is : **"+ OTP + "**"+"\n"+"if you recieved this mail by wrong ignore this message." );
        javaMailSender.send(message);
        System.out.println("mail send success");
    }

    @Override
    public int generateOTP() {
        int OTP = (int) ( 546721+ Math.random() * 999999);
        return OTP;
    }
    
}
