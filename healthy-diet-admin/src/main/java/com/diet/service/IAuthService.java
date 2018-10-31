package com.diet.service;

/**
 * @author LiuYu
 */
public interface IAuthService {
    String login(String userName, String password);

    void logout(String token);

    String refreshToken(String token);
}
