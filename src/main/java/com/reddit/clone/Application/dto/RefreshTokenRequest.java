package com.reddit.clone.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
    @NotNull
    private String userName;
}
