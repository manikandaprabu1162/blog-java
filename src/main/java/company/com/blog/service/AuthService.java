package company.com.blog.service;

import company.com.blog.dto.LoginRequest;
import company.com.blog.dto.RegisterRequest;
import company.com.blog.entity.User;
import company.com.blog.repository.UserRepository;
import company.com.blog.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtUtil jwtUtil;

  public User register(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new RuntimeException("Username already exists!");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("Email already exists!");
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setFullName(request.getFullName());
    user.setRole("USER");
    user.setActive(true);

    return userRepository.save(user);
  }

  public String login(LoginRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Generate JWT token
    return jwtUtil.generateToken(request.getUsername());
  }
}
