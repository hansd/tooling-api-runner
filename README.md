# tooling-api-runner
Builds a project via the Gradle tooling API multiple times and calculates the average execution time.

Running Gradle from the command line introduces overhead as a JVM client is always fired up to communicate with the daemon. When you use the tooling API in a warmed up JVM this effect goes away and the numbers give a better indication for pure Gradle performance. It also gives you the same experience compared to running Gradle from the IDE. When the runner computes the execution average, it does not include the first invocation of the build run as this includes the first time loading cost of the tooling API.

## Usage

This project use the Gradle application plugin. To get going you need to execute first `./gradlew installDist`. After this you can use the runner: ./build/install/tooling-api-runner/bin/tooling-api-runner [PATH_TO_PROJECT_TO_BUILD] [TASKSNAMES TO BE EXECUTED] [NUMBER OF EXECUTIONS] [PARALLEL true/false] [GRADLE_DISTRIBUTION_TO_BE_USED]

The last argument is option. If not specified it uses either the wrapper defined for the project you want to build. Or if not wrapper is defined in that project, it used the Gradle distribution installed on this machine.
