package com.example.demo1.dao.response;

import com.example.demo1.dao.entity.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface JwtBlacklistReponsitory extends JpaRepository<JwtBlacklist, Integer> {
    boolean existsByToken(String token);

    JwtBlacklist findByToken(String token);



}
