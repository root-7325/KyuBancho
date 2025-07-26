![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk) ![Netty](https://img.shields.io/badge/Netty-4.x-blue) ![Status](https://img.shields.io/badge/Status-Beta-yellow) ![License](https://img.shields.io/badge/License-MIT-green)

# KyuBancho - an osu!Bancho emulator 🪄

**KyuBancho** is an **experimental server emulator** for some circle-clicking game written on **Java**.

![Running KyuBancho](screenshots/main.png)

## ⚠️ Before all

While most planned features **are implemented**, some **core functionality** may still be **incomplete**. For more full-feature experience, you'll probably need own *score-processing* backend.

## 🎯 What's Working

- **Network Layer**: Netty-based TCP server
- **Database**: MySQL integration with Hibernate ORM
- **Architecture**: DI with Guice, modular design
- **Game logic**: basic messages implementation (replay seeking include!), session management.

## 🚧 Planned features

1. Full implementation of **chat system**.
2. Implementation of **multiplayer**.
3. Custom BanchoBot implementation

## ⚙️ Requirements

- Java 17+
- Maven 3.6+
- MySQL Server

## 🚀 Usage

1. Start a MySQL server
2. Copy and fill out template located in `src/main/resources/application.conf.template` as `application.conf`
3. Patch osu! client
    - Server is tested **only** on build b497
    - *Automated patching solution may appear at some day*
4. Build and run the server
5. Create an account when running with: `useradd {username} {password}`

> You can place your .conf files (which use HOCON format) next to your .jar file or keep them inside in resources
> directory.

## ⭐️ Credits
- root7325 - creator & lead dev :p

## 🌐 References

Some parts of this project are based on the following code:

- [ekgame/bancho-api](https://github.com/ekgame/bancho-api)
    - [ByteDataInputStream.java](https://github.com/ekgame/bancho-api/blob/master/src/main/java/lt/ekgame/bancho/api/packets/ByteDataInputStream.java) -
      Reference implementation for `ByteBufUtils`
    - [ByteDataOutputStream.java](https://github.com/ekgame/bancho-api/blob/master/src/main/java/lt/ekgame/bancho/api/packets/ByteDataOutputStream.java) -
      Reference implementation for `ByteBufUtils`

## 📃 License

This project is licensed under the [MIT License](LICENSE) - so do whatever you want with it, just don't blame me if
something goes sideways.