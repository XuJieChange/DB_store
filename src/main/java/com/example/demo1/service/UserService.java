package com.example.demo1.service;

import com.example.demo1.dao.UsersRepository;
import com.example.demo1.dto.UserDTO;
import com.example.demo1.dao.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;
    public Users createUser(UserDTO userDTO){
        Users users=new Users();
        users.setId(userDTO.getId());
        users.setName(userDTO.getName());
        users.setPassword(userDTO.getPassword());
        users.setIs_deleted(userDTO.getIs_delete());
        users.setEmail(userDTO.getEmail());
        userDTO.setCreated_at(LocalDateTime.now());
//        userDTO.setUpdated_at(LocalDateTime.now());
        usersRepository.save(users);
        return users;
    }

    public Users updateUser(UserDTO userDTO) {
        Users existingUser = usersRepository.findById(userDTO.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setName(userDTO.getName());
            existingUser.setPassword(userDTO.getPassword());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setIs_deleted(userDTO.getIs_delete());
            userDTO.setCreated_at(existingUser.getCreated_at());
            userDTO.setUpdated_at(LocalDateTime.now());
            return usersRepository.save(existingUser);

        }
        return null;
    }

    public boolean deleteUser(Integer id) {
        Optional<Users> existingUser = usersRepository.findById(id);
        if (existingUser.isPresent()) {
            usersRepository.delete(existingUser.get());
            return true;  // 成功刪除
        }
        return false;  // 員工不存在
    }

    public UserDTO findUserById(Integer userId) {
        Users existingUser = usersRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(existingUser.getId());
            userDTO.setName(existingUser.getName());
            userDTO.setEmail(existingUser.getEmail());
            userDTO.setPassword(existingUser.getPassword());
            userDTO.isIs_delete(existingUser.getIs_deleted());
            userDTO.setCreated_at(existingUser.getCreated_at());
            userDTO.setUpdated_at(existingUser.getUpdated_at());

            return userDTO;
        }
        return null;
    }
    public List<UserDTO> searchUsersByEmail(String email) {
        Optional<Users> userList = usersRepository.findByEmail(email);
        if (userList.isPresent()) {
            return Collections.singletonList(convertToDTO(userList.get()));
        }
        return Collections.emptyList();
    }//檢查 Optional 是否有值（通過 isPresent() 方法）。如果有值，則返回包含轉換後的DTO的列表；否則，返回空列表。

    private UserDTO convertToDTO(Users users) {
        UserDTO dto = new UserDTO();
        dto.setId(users.getId());
        dto.setName(users.getName());
        dto.setEmail(users.getEmail());
        dto.setPassword(users.getPassword());
        dto.isIs_delete(users.getIs_deleted());
        dto.setCreated_at(users.getCreated_at());
        dto.setUpdated_at(users.getUpdated_at());
        // 如果EmployeeDTO还有其他属性，例如created_at或updated_at，也应在这里设置
        return dto;
    }
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return usersRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}

