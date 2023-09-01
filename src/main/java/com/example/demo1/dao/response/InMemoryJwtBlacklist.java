package com.example.demo1.dao.response;

import com.example.demo1.dao.entity.JwtBlacklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryJwtBlacklist {

    @Autowired
    private JwtBlacklistReponsitory jwtBlacklistReponsitory;
    private final Set<String> blacklistedTokens = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void blacklist(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isBlacklisted(String token) {
        System.out.println("isBlacklistedStart");
        System.out.println("token:"+token);
        JwtBlacklist jwtBlacklist = jwtBlacklistReponsitory.findByToken(token);
        if(jwtBlacklist != null){
            return true;
        }
        return false;
    }
}
