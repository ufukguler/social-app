
### run mysql & rabbitmq w/ docker
`docker-compose up`

### controllers
JWT uses Bearer Token, to send a request to the server first you need to send a post request to /api/register with JSON data (username, password) then send a post request to /api/login and you will get your token.


AuthController
```
POST /api/login                         | Authentication with JWT
```

RegisterController
```
POST /api/register                      | Authorization - Generates JWT
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
