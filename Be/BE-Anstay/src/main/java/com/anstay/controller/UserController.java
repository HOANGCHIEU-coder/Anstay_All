package com.anstay.controller;

import com.anstay.entity.User;
import com.anstay.repository.UserRepository;
import com.anstay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setFullName(userDetails.getFullName());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());

            // Chỉ cập nhật mật khẩu nếu có giá trị mới
            if (userDetails.getPassword() != null && !userDetails.getPassword().trim().isEmpty()) {
                user.setPassword(userDetails.getPassword()); // Có thể hash trước khi lưu
            }

            user.setAvatar(userDetails.getAvatar());
            user.setAddress(userDetails.getAddress());
            user.setRole(userDetails.getRole());
            user.setDob(userDetails.getDob());
            user.setGender(userDetails.getGender());
            user.setVerified(userDetails.getVerified());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-role")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam String role) {
        System.out.println("🔍 API called with role: " + role); // Debug

        try {
            List<User> users = userService.getUsersByRole(role);
            return ResponseEntity.ok(users); // Trả về [] nếu không có user nào
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Trả về lỗi nếu role không hợp lệ
        }
    }



}
