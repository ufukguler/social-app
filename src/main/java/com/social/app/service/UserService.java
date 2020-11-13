package com.social.app.service;

import com.social.app.entity.Channel;
import com.social.app.entity.User;
import com.social.app.model.ChannelDto;
import com.social.app.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean subscribe(ChannelDto channelDto, UserDto userDto) throws Exception;

    boolean unsubscribe(ChannelDto channelDto, UserDto userDto) throws Exception;

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByName(String name);

    List<Channel> findSubscribedChannelsById(Long id);

}
