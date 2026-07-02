package service;

import entity.User;
import repository.UserRepository;
import utils.ValidationUtil;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public User login(String username, String password) throws Exception {
        if (ValidationUtil.isEmpty(username) || ValidationUtil.isEmpty(password)) {
            throw new IllegalArgumentException("Username dan password wajib diisi.");
        }

        return userRepository.findByUsernameAndPassword(username, password);
    }
}
