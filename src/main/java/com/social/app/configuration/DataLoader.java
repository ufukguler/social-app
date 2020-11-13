package com.social.app.configuration;

import com.social.app.entity.Channel;
import com.social.app.entity.Role;
import com.social.app.entity.User;
import com.social.app.repository.ChannelRepository;
import com.social.app.repository.RoleRepository;
import com.social.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /**
         * Role
         */
        Role role = new Role();
        role.setName("ROLE_USER");

        if (!roleRepository.existsByName("ROLE_USER"))
            roleRepository.save(role);

        /**
         * users
         */
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword(passwordEncoder.encode("123"));
        user1.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        user1.setActive(true);
        if (!userRepository.findByUsername("user1").isPresent())
            userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword(passwordEncoder.encode("123"));
        user2.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        user2.setActive(true);
        if (!userRepository.findByUsername("user2").isPresent())
            userRepository.save(user2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword(passwordEncoder.encode("123"));
        user3.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        user3.setActive(true);
        if (!userRepository.findByUsername("user3").isPresent())
            userRepository.save(user3);

        /**
         * channels
         */
        Channel channel1 = new Channel();
        channel1.setName("channel 1");
        channel1.setOwner(userRepository.findByUsername("user1").get());
        channel1.setSubscribers(Arrays.asList(
                userRepository.findByUsername("user1").get(),
                userRepository.findByUsername("user2").get(),
                userRepository.findByUsername("user3").get()
        ));
        if (!channelRepository.findByName("channel 1").isPresent())
            channelRepository.save(channel1);

        Channel channel2 = new Channel();
        channel2.setName("channel 2");
        channel2.setOwner(userRepository.findByUsername("user2").get());
        channel2.setSubscribers(Arrays.asList(
                userRepository.findByUsername("user1").get(),
                userRepository.findByUsername("user2").get()
        ));
        if (!channelRepository.findByName("channel 2").isPresent())
            channelRepository.save(channel2);

        Channel channel3 = new Channel();
        channel3.setName("channel 3");
        channel3.setOwner(userRepository.findByUsername("user3").get());
        if (!channelRepository.findByName("channel 3").isPresent())
            channelRepository.save(channel3);
    }
}
