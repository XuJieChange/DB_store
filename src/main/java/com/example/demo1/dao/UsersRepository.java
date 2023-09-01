package com.example.demo1.dao;

import com.example.demo1.dao.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository <Users,Integer> {
    //自定義查詢
    Optional<Users> findByEmail(String email);
}