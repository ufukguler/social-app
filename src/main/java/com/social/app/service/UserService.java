package com.social.app.service;

import com.social.app.entity.Channel;
import com.social.app.entity.User;
import com.social.app.model.NotificationDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByName(String name);

    List<Channel> findSubscribedChannelsById(Long id);

    NotificationDto shareOnChannel(NotificationDto message);
}
