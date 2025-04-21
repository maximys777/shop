package com.maximys777.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class UserResponse {
    private String userName;
    private String userGmail;
    private String userRole;
    private LocalDateTime userCreatedAt;
}
