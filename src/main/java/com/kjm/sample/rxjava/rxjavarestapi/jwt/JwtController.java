package com.kjm.sample.rxjava.rxjavarestapi.jwt;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class JwtController {
    // private final JwtTokenProvider jwtTokenProvider;
    // private final AuthenticationManager authenticationManager;

    // @PostMapping("/v1.0/login")
    // public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    //     try {
    //         authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(
    //                 loginRequest.getUsername(), 
    //                 loginRequest.getPassword()
    //             )
    //         );

    //         List<String> roles = List.of("ROLE_USER"); // 실제 구현에서는 DB에서 가져옴
    //         String accessToken = jwtTokenProvider.createAccessToken(loginRequest.getUsername(), roles);
    //         String refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.getUsername());

    //         return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    //     } catch (AuthenticationException e) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    //     }
    // }

    // @PostMapping("/v1.0/refresh")
    // public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
    //     try {
    //         if (jwtTokenProvider.validateToken(request.getRefreshToken())) {
    //             String username = jwtTokenProvider.getUsername(request.getRefreshToken());
    //             List<String> roles = List.of("ROLE_USER"); // 실제 구현에서는 DB에서 가져옴
    //             String newAccessToken = jwtTokenProvider.createAccessToken(username, roles);
                
    //             return ResponseEntity.ok(new JwtResponse(newAccessToken, request.getRefreshToken()));
    //         }
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refreshing token");
    //     }
    // }
}
