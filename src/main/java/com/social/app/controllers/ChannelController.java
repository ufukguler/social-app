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
import java.util.Optional;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

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
     *
     * @param id
     * @param principal
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}/subscribe")
    @ResponseBody
    public ResponseEntity<?> subscribe(@PathVariable Long id, Principal principal) throws Exception {
        channelService.subscribe(id, principal);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{id}/unsubscribe")
    @ResponseBody
    public ResponseEntity<?> unsubscribe(@PathVariable Long id, Principal principal) throws Exception {
        channelService.unsubscribe(null, null);
        return ResponseEntity.ok(true);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}/info")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(channelService.findById(id));
    }

    @GetMapping("/{id}/delete")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Channel channel = channelService.delete(id);
        if (channel.isActive()) {
            return ResponseEntity.of(Optional.of("Error!"));
        }
        return ResponseEntity.ok(channelService.delete(id));
    }

}
