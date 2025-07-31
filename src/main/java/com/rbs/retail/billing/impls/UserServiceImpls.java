package com.rbs.retail.billing.impls;

import com.rbs.retail.billing.dto.UserDto;
import com.rbs.retail.billing.entities.UserEntity;
import com.rbs.retail.billing.repositories.UserRepository;
import com.rbs.retail.billing.response.UserResponse;
import com.rbs.retail.billing.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpls implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserDto request) {

        UserEntity newUser = convertToEntity(request);

        newUser = userRepository.save(newUser);

        return convertToResponse(newUser);
    }

    private UserResponse convertToResponse(UserEntity newUser) {

        return UserResponse.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .userId(newUser.getUserId())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .role(newUser.getRole())
                .build();
    }

    private UserEntity convertToEntity(UserDto request) {

        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole().toUpperCase())
                .name(request.getName())
                .build();
   }

    @Override
    public String getUserRole(String email) {

        UserEntity existingUser = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(" user not found with this email " + email));
        return existingUser.getRole();
    }

    @Override
    public List<UserResponse> readUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> convertToResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {

        UserEntity existingUser = userRepository.findByUserId(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        userRepository.delete(existingUser);
    }
}
