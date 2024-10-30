# SwiftQueueServer (QS)

SwiftQueueServer (QS) is designed for extreme player counts by cutting down on unnecessary functionality. It serves as a perfect buffer between your network and players during peak influx times, preventing strain on your server.

Just like large servers such as Hypixel, this approach helps maintain stability when many players are trying to join.

This idea was originally suggested by Sprock / Cobble Sword, who didn’t finish it. We’re taking the lead to make it happen and improve it even further!

## Download

You can download the latest stable release from the [releases section](https://github.com/yourusername/SwiftQueueServer/releases). The API can be obtained from our Discord server.

## Requirements

- A server connected to a BungeeCord proxy

## How to Setup

1. Download the plugin and place the JAR file in the `plugins` folder of your spigot server (not in the BungeeCord server).
2. Start your server. This will generate a `config.yml` file.
3. Stop the server and configure `config.yml` according to your preferences, including setting up the servers you want players to queue for.
4. Restart your server, and the plugin will be operational.

## Commands

Here are some of the commands available in SwiftQueueServer:
- `/queue` - Check your positon in the queue. **(Under development)**
- `/system on` - Enable the queue system.
- `/system off` - Disable the queue system.
- `/queue join <server>` - Join a queue for a specific server.
- `/queue leave` - Leave the queue for the server.
- `/queue list` - View the list of servers you can queue for. **(Under development)**
- `/queue info [server_name]` - Get information about the specified server in the queue. **(Under development)**

## Building

To compile SwiftQueueServer, you’ll need:

- JDK 8 (or above)
- Maven: run `mvn clean install` to build.

## Contributing

Feel free to contribute! Whether you have suggestions, bug fixes, or new features in mind.
