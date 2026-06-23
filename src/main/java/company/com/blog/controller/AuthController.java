package company.com.blog.controller;

import company.com.blog.dto.AuthResponse;
import company.com.blog.dto.LoginRequest;
import company.com.blog.dto.RegisterRequest;
import company.com.blog.entity.User;
import company.com.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
      User user = authService.register(request);
      return ResponseEntity.ok(
          new AuthResponse(
              "User registered successfully!", user.getUsername(), user.getRole(), true));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(new AuthResponse(e.getMessage(), null, null, false));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
      String message = authService.login(request);
      return ResponseEntity.ok(new AuthResponse(message, request.getUsername(), "USER", true));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new AuthResponse("Invalid username or password!", null, null, false));
    }
  }
}
