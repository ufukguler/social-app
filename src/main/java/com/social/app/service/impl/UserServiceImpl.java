package com.social.app.service.impl;

import com.social.app.entity.Channel;
import com.social.app.entity.User;
import com.social.app.model.NotificationDto;
import com.social.app.repository.UserRepository;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbit.routing.name}")
    private String routingName;

    @Value("${app.rabbit.exchange.name}")
    private String exchangeName;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<Channel> findSubscribedChannelsById(Long id) {
        Optional<User> user = userRepository.findById(id);
        List<Channel> channels = new ArrayList<>();
        user.get().getSubbedChannels().forEach(channel -> {
            channel.setOwner(new User());
            channel.setSubscribers(new ArrayList<>());
            channels.add(channel);
        });
        return channels;
    }

    @Override
    public NotificationDto shareOnChannel(NotificationDto notification) {
        rabbitTemplate.convertAndSend(exchangeName, routingName, notification);
        return notification;
    }

}
