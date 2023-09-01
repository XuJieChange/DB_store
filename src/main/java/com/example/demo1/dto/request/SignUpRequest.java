package com.example.demo1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private Integer id;
    private String email;
    private String name;
    private String password;
}
