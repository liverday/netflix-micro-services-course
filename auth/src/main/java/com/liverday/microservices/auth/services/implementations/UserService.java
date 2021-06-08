package com.liverday.microservices.auth.services.implementations;

import com.liverday.microservices.auth.model.Permission;
import com.liverday.microservices.auth.model.User;
import com.liverday.microservices.auth.model.dto.CreateUserDTO;
import com.liverday.microservices.auth.repositories.PermissionRepository;
import com.liverday.microservices.auth.repositories.UsersRepository;
import com.liverday.microservices.auth.services.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("unused")
public class UserService implements IUsersService {
    private final UsersRepository usersRepository;
    private final PermissionRepository permissionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = usersRepository.findByUserName(userName);

        if (user != null) {
            return user;
        }

        throw new UsernameNotFoundException("Usuário não encontrado");
    }

    @Override
    public User findByUserName(String userName) {
        return usersRepository.findByUserName(userName);
    }

    @Override
    public User createUser(CreateUserDTO createUserDTO) throws Exception {
        User existingUser = findByUserName(createUserDTO.getUserName());

        if (existingUser != null) {
            throw new Exception("Usuário já existe");
        }

        Permission permission = permissionRepository.findByDescription("USER");
        User user = CreateUserDTO.create(createUserDTO, User.class);

        user.setPermissions(List.of(permission));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return usersRepository.save(user);
    }
}
