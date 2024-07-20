package com.spring_boot.security_example.service;

import com.spring_boot.security_example.entity.PasswordRestToken;
import com.spring_boot.security_example.entity.User;
import com.spring_boot.security_example.entity.VerificationToken;
import com.spring_boot.security_example.repository.PasswordRestTokenRepository;
import lombok.RequiredArgsConstructor;
import com.spring_boot.security_example.model.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.spring_boot.security_example.repository.UserRepository;
import com.spring_boot.security_example.repository.VerificationTokenRepository;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordRestTokenRepository passwordRestTokenRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken == null){
            return "invalid";
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if(verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);

        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordRestToken passwordRestToken = new PasswordRestToken(user, token);
        passwordRestTokenRepository.save(passwordRestToken);
    }

    @Override
    public String validatePassowrdResetToken(String token) {
        PasswordRestToken passwordRestToken = passwordRestTokenRepository.findByToken(token);

        if(passwordRestToken == null){
            return "invalid";
        }

        User user = passwordRestToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if(passwordRestToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0){
            passwordRestTokenRepository.delete(passwordRestToken);
            return "expired";
        }

        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordRestTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
