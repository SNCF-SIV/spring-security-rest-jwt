package com.sncf.siv.poc.security.repository;


import com.sncf.siv.poc.security.model.AppUser;
import org.springframework.data.repository.CrudRepository;


public interface AppUserRepository extends CrudRepository<AppUser, String> {

    AppUser findByUsername(String username);
}


