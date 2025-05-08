![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk) ![Netty](https://img.shields.io/badge/Netty-4.x-blue) ![Status](https://img.shields.io/badge/Status-Early_Development-red) ![License](https://img.shields.io/badge/License-MIT-green)
# KyuBancho - an osu!Bancho emulator
**KyuBancho** is an **experimental server emulator** for some circle-clicking game written on **Java**.

## ⚠️ Before all
This project is in **early development state**. Many core features aren't implement or incomplete. The code **may** contain bugs and **questionable** solutions!

## Implemented features
1. Netty implemented TCP server
2. Basic user session management
3. Ping/pong (so you don't get **Osu_Exit** just because)
4. Login stub (any credentials are valid)
5. Basic user status update

## Planned features
1. MySQL Database with Hibernate
2. Chat system
3. Own BanchoBot
4. Replay seeking

## Requirements
- Java 17 or higher
- Maven 3.6 or higher

## Usage
1. Patch osu! client by yourself. (automated solution may appear at someday)
2. Clone this repo and run it either via your IDE, or via `mvn package` and then `java -jar target/kyu-bancho-0.0.1.jar` 

## License
This project is licensed under the [MIT License](LICENSE) - so do whatever you want with it, just don't blame me if something goes sideways.