﻿# AshesMS 
AshesMS is a server emulator for Global MapleStory (GMS) version 83 based off of the Cosmic open source project.

## Introduction

Cosmic launched on March 2021. It is based on code from a long line of server emulators spanning over a decade - starting with OdinMS (2008) and ending with HeavenMS (2019).

This is mainly a Java based project, but there are also a bunch of scripts written in JavaScript.

Head developer and maintainer: marlo\
Contributors: Ponk, a lot of people over the years, and hopefully more to come. Big thanks to everyone who has contributed so far!

Join the Discord server where most of the discussions take place: https://discord.gg/x4qHEUyn
### Goals
What we are working towards.
* __Vanilla gameplay__ - stay as close to the original game as possible (within reason).
* __Ease of use__ - getting started should be frictionless and contributing to the project straightforward.
* __Reduce technical debt__ - making changes should be easy without causing unintended side effects.
* __Modern tools & technologies__ - stay appealing by continuously improving the code and the project as a whole. 

### Non-goals
Explicitly excluded from the scope of the project.
* __Custom gameplay features__ - existing custom features will be removed over time and new ones are unlikely to be added.
* __Client development__ - this project is focused on the server. Please go elsewhere for client related questions.
* __Public server__ - there will not be an official Cosmic server open to the public. Feel free to launch your own server __at your own risk__. No support will be provided.

## Project setup

### Contribute
Outside contributions aren't being accepted at the moment, but feel free to make feature requests in the Discord.

## Getting started
Follow along as I go through the steps to play the game on your local computer from start to finish. I won't go into extreme detail, so if you don't have prior experience with Java or git, you might struggle.

We will set up the following:
- Database - the database is used by the server to store game data such as accounts, characters and inventory items.
- Server - the server is the "brain" and routes network traffic between the clients.
- Client - the client is the application used to _play the game_, i.e. MapleStory.exe.

### Database 
You will start by installing the database server and client, and then run some scripts to prepare it for the server.

#### Steps
1. Download and install MySQL Server 8+. You will have to set a root password, make sure you don't lose it because you will need it later.
2. Run all four scripts located in database/seed in order. `mysql -u root < file-name.sql` (to be replaced by a migration framework)
3. The database is ready!

### Server
You will start by cloning the repository, then configure the database properties and lastly start the server.

#### Prerequisites
* [asdf](https://asdf-vm.com/): optional, used to manage Java versions
* Java 21
* Gradle 8.9

#### Steps
1. Clone AshesMS into a new project.
2. Make sure the "DB_PASS" is set as an environment variable in your shell as the MySQL `root` user password.
3. Start the server using Gradle, i.e. `gradle run`
4. If you see "Ashes is now online" in the console, it means the server is online and ready to serve traffic. Yay!

#### Jar
Another option is to start the server from a terminal by building and executing a `.jar` and running it on the JVM. Run `gradle build` which will produce a "fat jar" which contains all the dependencies. Execute with `java -Xmx2048m -Dwz-path=wz -jar AshesMS-all.jar`

### Client
You will start by installing the game with the old installer, then overwrite some WZ files with our custom ones, and lastly get the localhost executable in place.

#### Steps

1. Download _MapleGlobal-v83-setup.exe_ from my [Google Drive](https://drive.google.com/drive/folders/1hgnb92MGL6xqEp9szEMBh0K9pSJcJ6IT). This is the official installer from back then.
2. Install it in a directory of your choice.
3. Delete the following files from the installation directory: _HShield_ (entire directory), _ASPLnchr.exe_, _MapleStory.exe_, and _Patcher.exe_.
4. Download _CosmicWZ-2024-07-17-v0.14.0.zip_ from my [Google Drive](https://drive.google.com/drive/folders/1hgnb92MGL6xqEp9szEMBh0K9pSJcJ6IT).
5. Unzip it and copy all .wz-files into the installation directory. Replace the existing ones.
6. Download _HeavenMS-localhost-WINDOW.exe_ from [hostr.co](https://hostr.co/amuX5SLeeVZx). This is a client modified to connect to your localhost instead of Nexon's server (along with some fixes and custom changes). 
   - Your antivirus will likely detect the file as a trojan or similar and automatically delete it. To prevent this from happening, add your _Downloads_ directory and the installation directory as exclusions in your antivirus software. On W11, this is under "Virus & threat protection settings" -> "Add or remove exclusions". 
7. Move _HeavenMS-localhost-WINDOW.exe_ into the installation directory.
8. Done! Double-click the exe and the game should start.
   - The client may be a bit fiddly. Sometimes it won't start, but if you see "Client connected" in the server console it's a good indication. Try spam-clicking it like 10+ times, that usually works for me.

**Important note about localhost clients**: these executables are red-flagged by antivirus tools as potentially malicious software.
This happens due to the reverse engineering methods that were applied onto these software artifacts. 
The one provided here has been in use for years already and posed no harm so far, so it is assumed to be safe.

#### Logging in
At this point, you can log in to the admin account using the following credentials:
* Username: "admin"
* Password: "admin"
* Pin: "0000"
* Pic: "000000"

Or create a regular account by typing in your desired username & password and attempting to log in. This "automatic registration" feature lets you create new accounts to play around with. It is enabled by default (see _config.yaml_).

#### Entering the game
Create a new character as you normally would, and then select it to enter the game. Hooray, finally we're in!

If you log in to the "Admin" character, you'll notice that the character looks almost invisible. This is hide mode, which is enabled by default when you log in to a GM character. You won't be visible to normal players and no mobs will move if you're alone on the map. Toggle hide mode on or off by typing "@hide" in the in-game chat.

Hide is one of many commands available to players, type "@commands" to see the full list. Higher ranked GMs have access to more powerful commands.

That's it, have fun playing around in game! 

## Advanced concepts
Some slightly more advanced concepts that might be useful once you're up and running.

### Edit client ip
1. Download and install a hex editor: [HxD](https://mh-nexus.de/en/hxd/)
2. Start HxD and open your client exe (I recommend making a copy of it first). At this point you should see a bunch of hex codes and a "Decoded text" column to the right of it.
3. Ctrl+f and search for Text-string "127.0.0.1". You should find three occurrences right above each other.
4. Place your cursor before the first "127" and start typing the desired ip, overwriting what is already there. Do the same on the other two and click on Save.
5. Done! Now the client will attempt to connect to that ip address instead when you launch it.

### WZ files
WZ files are the asset/data files required by the client and server. Typically, [HaRepacker-resurrected](https://github.com/lastbattle/Harepacker-resurrected) is used to handle (view, edit, export) the .wz files.

The client can read the .wz files directly, but the server requires them in XML format. The server also does not make use of the sprites, which is the motivation for different kinds of exporting. 
HaRepacker allows you to export to "Private server", which is the .img files packaged in the .wz stripped of sprites and converted to XML. This takes much less disk space.

This server requires custom .wz files (unfortunately), as you may have noted during installation of the client. The intention is for these to be removed eventually and to solely run on vanilla .wz files.

#### WZ editing
* Use the HaRepacker-resurrected editor, encryption "GMS (old)".
* Open the desired .wz for editing and use the node hierarchy to make the desired changes (copy/pasting nodes may be unreliable in rare scenarios).
* Save the changed .wz, overwriting the original content at the client folder.
* Finally, re-export (using the "Private Server" exporting option) the changed XMLs into the server's .wz XML files (found in the "wz" directory), overwriting the old contents.

Make sure to always export from the client .wz files to the server XML, and not the other way around. 

Editing the client .wz without exporting to the server may lead to strange behavior.

### Client features
For more information about the client and its features, see [HeavenMS on GitHub](https://github.com/ronancpl/HeavenMS#download-items).

Some notable features:
* Opens in window mode by default
* Uncapped max speed
