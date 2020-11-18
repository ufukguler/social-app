package com.social.app.service;

import com.social.app.entity.Channel;
import com.social.app.model.ChannelDto;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ChannelService {
    Channel save(ChannelDto channelDto, Principal principal);

    Channel delete(Long id);

    List<Channel> findAll();

    Optional<Channel> findById(Long id);

    void subscribe(Long id, Principal principal) throws Exception;
}
