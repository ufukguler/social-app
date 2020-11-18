AuthController
```
POST /api/login                         | Authorization - Generates JWT
```

RegisterController
```
POST /api/register                      | Authentication with JWT
```

ChannelController
```
POST /api/channels                      | If the user does not have a channel, creates a new channel.
GET  /api/channels                      | List channels.
GET  /api/channels/{id}/info            | List specific channel.
GET  /api/channels/{id}/delete          | Set active to false of user's channel. User must be the owner of channel.
GET  /api/channels/{id}/subscribe       | Subscribe to a channel.
GET  /api/channels/{id}/unsubscribe     | Unsubscribe from a channel.
```

UserController
```
GET  /api/users                         | List all users.
POST /api/users/share                   | Share a notification.
GET  /api/users/{id}/info               | Specific user's information.
GET  /api/users/subscribed              | List subscribed channels.
```