package com.vehiclemanager.vehiclemanager.services;

import com.vehiclemanager.vehiclemanager.dto.CreateUserDto;
import com.vehiclemanager.vehiclemanager.entities.Address;
import com.vehiclemanager.vehiclemanager.entities.Role;
import com.vehiclemanager.vehiclemanager.entities.User;
import com.vehiclemanager.vehiclemanager.repository.RoleRepository;
import com.vehiclemanager.vehiclemanager.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(CreateUserDto dto) {
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var address = new Address();
        address.setStreet(dto.createAddressDto().street());
        address.setNumber(dto.createAddressDto().number());
        address.setNeighborhood(dto.createAddressDto().neighborhood());
        address.setComplement(dto.createAddressDto().complement());
        address.setCep(dto.createAddressDto().cep());
        address.setCity(dto.createAddressDto().city());
        address.setState(dto.createAddressDto().state());

        var user = new User();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setName(dto.email());
        user.setCnpj(dto.cnpj());
        user.setAddress(address);
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public void updateUser(UUID userId, CreateUserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (dto.cnpj() != null) {
            user.setCnpj(dto.cnpj());
        }
        if (dto.name() != null) {
            user.setName(dto.name());
        }
        if (dto.email() != null) {
            user.setEmail(dto.email());
        }
        if (dto.password() != null) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }
        var addressDto = dto.createAddressDto();
        var address = new Address();
        if (addressDto != null) {
            if (addressDto.street() != null) {
                address.setStreet(addressDto.street());
            }
            if (addressDto.number() != null) {
                address.setNumber(addressDto.number());
            }
            if (addressDto.neighborhood() != null) {
                address.setNeighborhood(addressDto.neighborhood());
            }
            if (addressDto.complement() != null) {
                address.setComplement(addressDto.complement());
            }
            if (addressDto.cep() != null) {
                address.setCep(addressDto.cep());
            }
            if (addressDto.city() != null) {
                address.setCity(addressDto.city());
            }
            if (addressDto.state() != null) {
                address.setState(addressDto.state());
            }
            user.setAddress(address);
        }


        userRepository.save(user);
    }
}
