package com.social.app.controllers;

import com.social.app.entity.Channel;
import com.social.app.model.ChannelDto;
import com.social.app.service.ChannelService;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;
    private final UserService userService;

    /**
     * list all channels
     *
     * @return
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(channelService.findAll());
    }

    /**
     * create new channel
     *
     * @param channelDto
     * @param principal
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody ChannelDto channelDto, Principal principal) {
        Channel channel = channelService.save(channelDto, principal);
        return ResponseEntity.ok(channel);
    }


    /**
     * subscribe to a channel by channel's id
     * @param id
     * @param principal
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}/subscribe")
    @ResponseBody
    public ResponseEntity<?> subscribe(@PathVariable Long id, Principal principal) throws Exception {
        channelService.subscribe(id, principal);
        return ResponseEntity.ok(userService.findByName(principal.getName()).get().getChannels());
    }
}
