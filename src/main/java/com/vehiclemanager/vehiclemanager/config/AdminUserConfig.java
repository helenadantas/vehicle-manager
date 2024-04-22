package com.vehiclemanager.vehiclemanager.config;

import com.vehiclemanager.vehiclemanager.entities.Address;
import com.vehiclemanager.vehiclemanager.entities.Role;
import com.vehiclemanager.vehiclemanager.entities.User;
import com.vehiclemanager.vehiclemanager.repository.RoleRepository;
import com.vehiclemanager.vehiclemanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByEmail("admin@admin.com");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var address = new Address();
                    address.setCep("58428-800");
                    address.setCity("Campina Grande");
                    address.setNumber("60");
                    address.setNeighborhood("Bela Vista");
                    address.setState("Paraiba");
                    address.setStreet("Rua X");
                    var user = new User();
                    user.setEmail("admin@admin.com");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    user.setCnpj("73.826.683/0001-04");
                    user.setAddress(address);
                    userRepository.save(user);
                }
        );
    }
}
