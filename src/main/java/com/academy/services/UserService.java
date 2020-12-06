package com.academy.services;

import com.academy.comparators.UsersIdComparator;
import com.academy.model.SearchUserDTO;
import com.academy.model.UserDTO;
import com.academy.persistence.entity.User;
import com.academy.persistence.entity.enums.Roles;
import com.academy.persistence.repositories.UserRepository;
import com.academy.persistence.specification.UserSpecification;
import com.academy.util.ContentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ContentUtils contentUtils;

    public UserDTO create(UserDTO userDTO) {
        User user = userRepository.save(getUser(userDTO));
        return getUserDto(user);
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return getUserDto(user);
    }

    public UserDTO save(UserDTO userDTO) {
        boolean profileEnable = true;
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if (user != null) {
            if (userDTO.getProfileEnable() != null)
                profileEnable = userDTO.getProfileEnable();
            if (userDTO.getRole() != null)
                user.setRole(userDTO.getRole());

            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setName(userDTO.getName());
            user.setSurname(userDTO.getSurname());
            user.setDescription(userDTO.getDescription());
            user.setProjectsId(userDTO.getProjectsId());
            user.setProfileEnable(profileEnable);
            user = userRepository.save(user);
        }
        return getUserDto(user);
    }

    public UserDTO adminSave(UserDTO userDTO) {
        if (userDTO.getProfileEnable() == null)
            userDTO.setProfileEnable(false);
        return save(userDTO);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByIdNotAndEmail(Long id, String email) {
        return userRepository.existsByIdNotAndEmail(id, email);
    }

    public List<UserDTO> findAllWithOutId(Long id) {
        List<User> userList = userRepository.findAllWithOutId(id);
        if (!userList.isEmpty())
            Collections.sort(userList, new UsersIdComparator());
        return getUserDtoList(userList);
    }

    public UserDTO findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        return getUserDto(user);
    }

    public List<UserDTO> findAllSearchIdNot(Long id, SearchUserDTO searchUserDTO) {
        UserSpecification userSpecification = new UserSpecification();
        List<User> userList = userRepository.findAll(userSpecification.findAllSearchIdNotSpecification(id, searchUserDTO));
        if (!userList.isEmpty())
            Collections.sort(userList, new UsersIdComparator());
        return getUserDtoList(userList);
    }

    private User getUser(UserDTO userDTO) {
        User user = null;
        if (userDTO != null) {
            user = new User();
            user.setEmail(contentUtils.getParam(userDTO.getEmail()));
            user.setPassword(contentUtils.getParam(userDTO.getPassword()));
            user.setName(contentUtils.getParam(userDTO.getName()));
            user.setSurname(contentUtils.getParam(userDTO.getSurname()));
            user.setDescription(contentUtils.getParam(userDTO.getDescription()));
            user.setProjectsId(contentUtils.getParam(userDTO.getProjectsId()));
            if (userDTO.getRole() == null) {
                user.setRole(Roles.User);
            } else {
                user.setRole(userDTO.getRole());
            }
            user.setProfileEnable(true);
        }
        return user;
    }

    private UserDTO getUserDto(User user) {
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(contentUtils.getParam(user.getEmail()));
            userDTO.setPassword(contentUtils.getParam(user.getPassword()));
            userDTO.setName(contentUtils.getParam(user.getName()));
            userDTO.setSurname(contentUtils.getParam(user.getSurname()));
            userDTO.setDescription(contentUtils.getParam(user.getDescription()));
            userDTO.setProjectsId(contentUtils.getParam(user.getProjectsId()));
            userDTO.setRole(user.getRole());
            userDTO.setProfileEnable(user.getProfileEnable());
        }
        return userDTO;
    }

    private List<UserDTO> getUserDtoList(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(contentUtils.getParam(user.getEmail()));
            userDTO.setPassword(contentUtils.getParam(user.getPassword()));
            userDTO.setName(contentUtils.getParam(user.getName()));
            userDTO.setSurname(contentUtils.getParam(user.getSurname()));
            userDTO.setDescription(contentUtils.getParam(user.getDescription()));
            userDTO.setProjectsId(contentUtils.getParam(user.getProjectsId()));
            userDTO.setRole(user.getRole());
            userDTO.setProfileEnable(user.getProfileEnable());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
