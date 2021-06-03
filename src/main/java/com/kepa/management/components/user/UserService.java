package com.kepa.management.components.user;

import com.kepa.management.components.assignment.Assignment;
import com.kepa.management.components.assignment.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private AssignmentRepository assignmentRepository;

    public UserService(UserRepository userRepository, AssignmentRepository assignmentRepository) {
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
    }

    List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    List<UserDto> findByLastName(String lastName) {
        return userRepository.findAllByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    UserDto save(UserDto user) {
        Optional<User> userByPesel = userRepository.findByPesel(user.getPesel());
        userByPesel.ifPresent(u -> {
            throw new DuplicateException();
        });
        return mapAndSaveUser(user);
    }

    UserDto update(UserDto user) {
        Optional<User> userByPesel = userRepository.findByPesel(user.getPesel());
        userByPesel.ifPresent(u -> {
            if (!u.getId().equals(user.getId()))
                throw new DuplicateException();
        });
        return mapAndSaveUser(user);
    }

    UserDto delete(Long userId){
        System.out.println(userId);
        Optional<User> user=userRepository.findById(userId);
        User userEntity = user.orElseThrow(UserNotFoundException::new);
        List<Assignment> assignments= userEntity.getAssignments();
        System.out.println(assignments);
        if(!assignments.isEmpty()) {
            assignments.forEach(assignment -> {
                assignmentRepository.deleteById(assignment.getId());
            });
        }
        userRepository.deleteById(userId);
        return UserMapper.toDto(userEntity);
    }

    Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDto);
    }

    List<UserAssignmentDto> getUserAssignments(Long userId) {
        return userRepository.findById(userId)
                .map(User::getAssignments)
                .orElseThrow(UserNotFoundException::new)
                .stream()
                .map(UserAssignmentMapper::toDto)
                .collect(Collectors.toList());
    }

    private UserDto mapAndSaveUser(UserDto user) {
        User userEntity = UserMapper.toEntity(user);
        User savedUser = userRepository.save(userEntity);
        return UserMapper.toDto(savedUser);
    }
}
