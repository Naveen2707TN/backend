package com.spring.auth.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.auth.Module.userModule;

public interface userRepo extends JpaRepository<userModule, Long> {

    userModule findByEmail(String email);

    userModule findByName(String name);
}
