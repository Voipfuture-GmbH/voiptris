# com.voipfuture.voiptris

This project contains UI code for the 'VoIPTris' test assignment. The task description can be found inside the /docs folder.

![Screenshot](https://github.com/Voipfuture-GmbH/voiptris/blob/master/screenshot.png)

## Building

Building the project requires JDK11 and Maven 3.6.1. Later versions *may* work but this is what we used during development.

## Running

You need to have a class implementing the com.voipfuture.com.voipfuture.voiptris.api.IGameController interface that is named "GameController" inside the Java default package namespace. The class needs to have a public constructor that takes two 'int' arguments, the width and height of the playing field to use while playing.

You can then start the game by running

    java -cp target/com.voipfuture.voiptris-core.jar:<YOUR JAR/CLASSES> com.voipfuture.com.voipfuture.voiptris.Main
