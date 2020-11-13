package com.social.app.service.impl;

import com.social.app.entity.Channel;
import com.social.app.entity.User;
import com.social.app.model.ChannelDto;
import com.social.app.repository.ChannelRepository;
import com.social.app.service.ChannelService;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserService userService;

    @Override
    public Channel save(ChannelDto channelDto, Principal principal) {
        Optional<Channel> optionalChannel = channelRepository.findByName(channelDto.getName());
        if (optionalChannel.isPresent()) {
            throw new IllegalArgumentException("channel already exist!");
        }
        User currentUser = userService.findByName(principal.getName()).get();

        if (currentUser.getChannel() != null) {
            throw new IllegalArgumentException("user already has a channel!");
        }

        Channel channel = new Channel();
        channel.setName(optionalChannel.get().getName());
        channel.setOwner(userService.findByName(principal.getName()).get());
        Channel savedChannel = channelRepository.save(channel);
        return channelRepository.save(channel);
    }

    @Override
    public Channel delete(ChannelDto channelDto) {
        Optional<Channel> optionalChannel = channelRepository.findByName(channelDto.getName());
        if (!optionalChannel.isPresent()) {
            throw new IllegalArgumentException("channel not exist!");
        }
        Channel channel = channelRepository.findByName(channelDto.getName()).get();
        channel.setActive(false);
        return channel;
    }

    @Override
    public List<Channel> findAll() {
        List<Channel> channels = channelRepository.findAll();
        //channels.forEach(channel -> channel.setSubscribers(null));
        return channels;
    }

    @Override
    public Optional<Channel> findById(Long id) {
        return channelRepository.findById(id);
    }
}
