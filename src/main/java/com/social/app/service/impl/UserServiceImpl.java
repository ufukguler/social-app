package com.social.app.service.impl;

import com.social.app.entity.Channel;
import com.social.app.entity.User;
import com.social.app.model.ChannelDto;
import com.social.app.model.UserDto;
import com.social.app.repository.ChannelRepository;
import com.social.app.repository.UserRepository;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

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

    private boolean isSubscribed(ChannelDto channelDto, UserDto userDto) {
        Optional<Channel> optionalChannel = channelRepository.findByName(channelDto.getName());
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        return (optionalChannel.isPresent() && optionalUser.isPresent()) ?
                optionalChannel.get().getSubscribers().contains(optionalUser.get()) : false;
    }
}
