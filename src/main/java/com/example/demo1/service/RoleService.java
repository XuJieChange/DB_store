package com.example.demo1.service;

import com.example.demo1.dao.RoleRepository;
import com.example.demo1.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo1.dao.entity.Roles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Roles createRole(RoleDTO roleDTO) {
        Roles role = new Roles();
        role.setId(roleDTO.getId());  // 注意：通常我们允许数据库自动生成ID，除非您有特定的需求
        role.setName(roleDTO.getName());
        roleDTO.setCreated_at(LocalDateTime.now());
        roleRepository.save(role);
        return role;
    }
    public List<RoleDTO> getAllRoles() {
        List<Roles> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private RoleDTO convertToDTO(Roles role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setCreated_at(role.getCreated_at());
        roleDTO.setUpdated_at(role.getUpdated_at());
        return roleDTO;
    }
    public Roles updateRole(RoleDTO roleDTO) {
        Roles existingRole=roleRepository.findById(roleDTO.getId()).orElse(null);
        if (existingRole!= null) {
            existingRole.setId(roleDTO.getId());
            existingRole.setName(roleDTO.getName());
            roleDTO.setCreated_at(existingRole.getCreated_at());
            roleDTO.setUpdated_at(LocalDateTime.now());
            return roleRepository.save(existingRole);
        }
        return null;
    }

    public boolean deleteRole(Integer id) {
        Optional<Roles> existingRole=roleRepository.findById(id);
        if(existingRole.isPresent()){
            roleRepository.delete(existingRole.get());
            return true;
        }
        return false;
    }
}
