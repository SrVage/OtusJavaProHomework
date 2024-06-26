package com.example.Homework_14.services;

import com.example.Homework_14.dto.UserDto;
import com.example.Homework_14.entities.User;
import com.example.Homework_14.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CRUDService<UserDto>{
    private final UserRepository userRepository;


    @Override
    public UserDto getById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("Нет пользователя с таким id"));
        return convertToDto(user);
    }

    @Override
    public Collection<UserDto> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserServiceImpl::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserDto item) {
        var entity = convertToEntity(item);
        var saveEntity = userRepository.save(entity);
        return convertToDto(saveEntity);
    }

    @Override
    public UserDto update(UserDto item) {
        var entity = convertToEntity(item);
        var saveEntity = userRepository.save(entity);
        return convertToDto(saveEntity);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Collection<UserDto> findAllUsersByFirstName(String firstName){
        return StreamSupport.stream(userRepository.findUsersByFirstName(firstName).spliterator(), false)
                .map(UserServiceImpl::convertToDto)
                .collect(Collectors.toList());
    }

    public Collection<UserDto> findAllUsersWithAgeGreaterThan(int age){
        return StreamSupport.stream(userRepository.findUsersWithAgeGreaterThan(age).spliterator(), false)
                .map(UserServiceImpl::convertToDto)
                .collect(Collectors.toList());
    }

    private static UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setSecondName(user.getSecondName());
        userDto.setAddress(user.getAddress());
        userDto.setAge(user.getAge());
        return userDto;
    }

    private static User convertToEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setAddress(userDto.getAddress());
        user.setAge(userDto.getAge());
        return user;
    }
}
