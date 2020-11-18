package com.social.app.service.impl;

import com.social.app.entity.Channel;
import com.social.app.entity.User;
import com.social.app.model.ChannelDto;
import com.social.app.model.UserDto;
import com.social.app.repository.ChannelRepository;
import com.social.app.service.ChannelService;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return savedChannel;
    }

    @Override
    public Channel delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Channel> optionalChannel = channelRepository.findById(id);
        User currentUser = userService.findByName(authentication.getName()).get();

        if (!optionalChannel.isPresent()) {
            throw new IllegalArgumentException("channel not exist!");
        }

        if (optionalChannel.get().getOwner().getId() != currentUser.getId()) {
            throw new IllegalArgumentException("current user does not own this channel!");
        }
        optionalChannel.get().setActive(false);
        channelRepository.save(optionalChannel.get());
        return optionalChannel.get();
    }

    @Override
    public List<Channel> findAll() {
        List<Channel> channels = channelRepository.findAll();
        //channels.forEach(channel -> channel.setSubscribers(null));
        return channels;
    }

    @Override
    public Optional<Channel> findById(Long id) {
        Optional<Channel> optionalChannel = channelRepository.findById(id);
        if (optionalChannel.isPresent()){
            //optionalChannel.get().getOwner().setChannel(null);
            return optionalChannel;
        }

        throw new IllegalArgumentException("channel not exist!");
    }

    @Override
    public void subscribe(Long id, Principal principal) throws Exception {
        Optional<Channel> channelOptional = channelRepository.findById(id);
        ChannelDto channelDto = new ChannelDto();
        channelDto.setName(channelOptional.get().getName());

        Optional<User> user = userService.findByName(principal.getName());
        UserDto userDto = new UserDto();
        userDto.setUsername(user.get().getUsername());
        userService.subscribe(channelDto, userDto);
    }
}
