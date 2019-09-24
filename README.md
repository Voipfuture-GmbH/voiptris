# voiptris-core

This project contains UI code for the 'VoIPTris' test assignment.

![Image of Yaktocat](https://octodex.github.com/images/yaktocat.png)

## Building

Building the project requires JDK11 and Maven 3.6.1. Later versions *may* work but this is what we used during development.

## Running

You need to have a class implementing the com.voipfuture.voiptris.api.IGameController interface that is named "GameController" inside the default package namespace.

You can then start the game by running

    java -cp target/voiptris-core.jar:<YOUR JAR> com.voipfuture.voiptris.Main
