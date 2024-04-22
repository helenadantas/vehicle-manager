package com.vehiclemanager.vehiclemanager.services;

import com.vehiclemanager.vehiclemanager.dto.*;
import com.vehiclemanager.vehiclemanager.entities.Address;
import com.vehiclemanager.vehiclemanager.entities.Role;
import com.vehiclemanager.vehiclemanager.entities.User;
import com.vehiclemanager.vehiclemanager.repository.RoleRepository;
import com.vehiclemanager.vehiclemanager.repository.UserRepository;
import com.vehiclemanager.vehiclemanager.repository.VehicleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       VehicleRepository vehicleRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
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

    public UserListDto getAllUsers(int page, int pageSize) {
        var userPage = userRepository.findAll(
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "name"));
        var userList = userPage.getContent().stream()
                .map(user ->
                        new UserDto(
                                user.getUserId(),
                                user.getEmail(),
                                user.getName(),
                                user.getCnpj(),
                                user.getAddress())
                )
                .collect(Collectors.toList());
        return new UserListDto(
                userList,
                page,
                pageSize,
                userPage.getTotalPages(),
                userPage.getTotalElements()
        );
    }

    public UserDto getUserById(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new UserDto(user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getCnpj(),
                user.getAddress());
    }

    public void deleteUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.getRoles().clear();

            user.setAddress(null);

            vehicleRepository.deleteByUser(user);

            userRepository.delete(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
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
