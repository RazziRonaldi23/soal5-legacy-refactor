package controller;

import entity.User;
import service.AuthService;

public class AuthController {
    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    public User login(String username, String password) throws Exception {
        return authService.login(username, password);
    }
}
