# voiptris

This project contains UI code for the 'VoIPTris' test assignment.

![Screenshot](https://github.com/Voipfuture-GmbH/voiptris/blob/master/screenshot.png)

## Building

Building the project requires JDK11 and Maven 3.6.1. Later versions *may* work but this is what we used during development.

## Running

You need to have a class implementing the com.voipfuture.voiptris.api.IGameController interface that is named "GameController" inside the default package namespace.

You can then start the game by running

    java -cp target/voiptris-core.jar:<YOUR JAR/CLASSES> com.voipfuture.voiptris.Main
