package com.social.app.service.impl;

import com.social.app.entity.Channel;
import com.social.app.entity.Notification;
import com.social.app.entity.User;
import com.social.app.model.ChannelDto;
import com.social.app.model.NotificationDto;
import com.social.app.model.UserDto;
import com.social.app.repository.ChannelRepository;
import com.social.app.repository.NotificationRepository;
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
    private final ChannelRepository channelRepository;
    private final RabbitTemplate rabbitTemplate;
    private final NotificationRepository notificationRepository;

    @Value("${app.rabbit.routing.name}")
    private String routingName;

    @Value("${app.rabbit.exchange.name}")
    private String exchangeName;

    @Override
    public boolean subscribe(ChannelDto channelDto, UserDto userDto) {
        Optional<Channel> optionalChannel = channelRepository.findByName(channelDto.getName());
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (isSubscribed(channelDto, userDto)) {
            throw new IllegalArgumentException("already subscribed!");
        }
        Channel channel = optionalChannel.get();
        User user = optionalUser.get();
        channel.getSubscribers().add(user);
        channel.setSubscribers(channel.getSubscribers());
        channelRepository.save(channel);
        return true;
    }

    @Override
    public boolean unsubscribe(ChannelDto channelDto, UserDto userDto) {
        return false;
    }

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
        user.get().getChannels().forEach(channel -> channels.add(channel));
        return channels;
    }

    @Override
    public NotificationDto shareOnChannel(NotificationDto notification) {
        rabbitTemplate.convertAndSend("exchange", "routing", notification);
        return notification;
    }

    @Override
    public Notification getNotifications() {
        return null;
    }

    private boolean isSubscribed(ChannelDto channelDto, UserDto userDto) {
        Optional<Channel> optionalChannel = channelRepository.findByName(channelDto.getName());
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        return (optionalChannel.isPresent() && optionalUser.isPresent()) ?
                optionalChannel.get().getSubscribers().contains(optionalUser.get()) : false;
    }
}
