package com.social.app.service.impl;

import com.social.app.entity.Channel;
import com.social.app.entity.User;
import com.social.app.model.ChannelDto;
import com.social.app.repository.ChannelRepository;
import com.social.app.service.ChannelService;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Channel already exist!");
        }
        User currentUser = userService.findByName(principal.getName()).get();

        if (currentUser.getChannel() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a channel!");
        }

        Channel channel = new Channel();
        channel.setName(channelDto.getName());
        channel.setOwner(userService.findByName(principal.getName()).get());
        Channel savedChannel = channelRepository.save(channel);
        return savedChannel;
    }

    @Override
    public Channel delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        isChannelExist(id);
        isUserExist(authentication);

        Optional<Channel> optionalChannel = channelRepository.findById(id);
        User currentUser = userService.findByName(authentication.getName()).get();

        if (optionalChannel.get().getOwner().getId() != currentUser.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not own this channel!");
        }

        if (!optionalChannel.get().getOwner().isActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This channel already deleted!");
        }

        optionalChannel.get().setActive(false);
        channelRepository.save(optionalChannel.get());
        return optionalChannel.get();
    }

    @Override
    public List<Channel> findAll() {
        List<Channel> channels = channelRepository.findAll();
        return channels;
    }

    @Override
    public Optional<Channel> findById(Long id) {
        Optional<Channel> optionalChannel = channelRepository.findById(id);
        isChannelExist(id);
        return optionalChannel;

    }

    @Override
    public boolean subscribe(Long id, Principal principal) {
        isChannelExist(id);
        isUserExist(principal);

        Channel channel = channelRepository.findById(id).get();
        User user = userService.findByName(principal.getName()).get();

        if (isSubscribed(channel, user.getSubbedChannels())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already subscribed!");
        }

        channel.getSubscribers().add(user);
        channelRepository.save(channel);
        return true;
    }

    @Override
    public boolean unsubscribe(Long id, Principal principal) {
        isChannelExist(id);
        isUserExist(principal);

        Channel channel = channelRepository.findById(id).get();
        User user = userService.findByName(principal.getName()).get();

        if (isSubscribed(channel, user.getSubbedChannels())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already subscribed!");
        }
        return false;
    }


    private boolean isSubscribed(Channel channel, List<Channel> channelList) {
        for (int i = 0; i < channelList.size(); i++)
            if (channelList.get(i).getId() == channel.getId())
                return true;

        return false;
    }

    private void isUserExist(Principal principal) {
        if (!userService.findByName(principal.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!");
        }
    }

    private void isChannelExist(Long id) {
        if (!channelRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Channel does not exist!");
        }
    }
}
