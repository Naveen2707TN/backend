package com.spring.auth.interfaces;

import com.spring.auth.Module.userModule;

public interface userServiceInterface {
    
    public userModule RegisterService(userModule userModule);

    public userModule LoginService(userModule userModule);

    public userModule verifyService(int code);

    public void sendMail(String email, String name);

    public int generateOTP();
}
