package com.example.demo.Service;


import com.example.demo.Entity.PasswordResetToken;
import com.example.demo.Entity.User;
import com.example.demo.Entity.VerificationToken;
import com.example.demo.Model.PasswordModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.PasswordResetTokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        //We must this verification token in database hence we need to create a new repository other than the normal one
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
        //now our user together with token are saved in DB, we now have to send email to the user for a successful
       // registeration.
    }

    @Override
    public String validateVerificatiionToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null) {
            return "INVALID TOKEN";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "EXPIRED";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "VALID";
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user,String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken  = PasswordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null) {
            return "INVALID TOKEN";
        }
        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "EXPIRED";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "VALID";
    }

    @Override
    public Optional<User> getUserByPasswordToken(String token) {
        return Optional.ofNullable(PasswordResetTokenRepository.findByToken(token).getUser());

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

