Gatling Setup for Manual Scripts:

Prerequisites:
First install and setup environment variables for below mentioned applications
1.	Java (JDK 8 and above)
2.	Maven
3.	Gatling

Once above setup is done then use InteliJ to setup your maven project with gatling.
1.	Install Scala plugin in InteliJ to create scala scripts for Gatling
2.	Start new project with “Archetype” and use below mentioned url for project setup
https://mvnrepository.com/artifact/io.gatling.highcharts/gatling-highcharts-maven-archetype
GroupId - io.gatling.highcharts
ArtifactId - gatling-highcharts-maven-archetype
Version - 3.5.1
3.	Once above setup is done and give name and location to your project and finish
4.	As soon project is created system will create pom file for project and build it which will take around 3-4 Minutes depending on package size and system
5.	Close the project once and re-open it to check everything is setup properly
6.	Create “simulations” directory under scala directory in project like shown below:

7.	Under simulations directory you can create your own scala scripts for testing.
8.	Before going for writing a test script in scala please make sure pom.xml file don’t have any error related to plugins or dependencies
9.	If there are any errors, then check right on IDE and select Maven tab.
10.	Right click and select build project that will fix issue
11.	Once it is done close and open project again
12.	While writing your test script it may ask to setup scala sdk, so open it and download the latest one
Now you will be able to write scala script without any issue and you will get all suggestion related to scala script

SCALA Scripts Structure:

1.	Script should have package name as simulations as we’ve created in above steps.
2.	Then we would need below mentioned imports **important for writing and executing scripts
a.	import.io.gatling.core.scenario.Simulation
b.	import.io.gatling.core.Predef._
c.	import.io.gatling.core.http.predef._
3.	Above ‘_’ means import everything from this package like we use * in java
4.	Then we’ve to start writing class for test case as – 
class TestAPI extends Simulation{}
5.	Every class that we will create must extends Simulation class to inherits all its features and methods.
6.	Inside this class we will have three sections as mentioned below:
a.	http configuration
b.	scenario
c.	setup
7.	Http configuration will be used to setup base URL of the website or web application in a variable that will be used in our step ‘c’ 
8.	Scenario will be used to create different scenarios to test with website or web-application mentioned in base URL. In one scala script we can create multiple scenarios make sure you should use the proper syntax. Such as using ‘.’ With every keyword in the script. If you miss that dot, then script will run only till that line and then skip rest of the cases.
9.	Setup is used to create how you would like to run performance with specified users and time and ramp up time etc. you can use below mentioned setup for testing.

Open Model
setUp(
  scn.inject(
    nothingFor(4.seconds), // 1
    atOnceUsers(10), // 2
    rampUsers(10).during(5.seconds), // 3
    constantUsersPerSec(20).during(15.seconds), // 4
    constantUsersPerSec(20).during(15.seconds).randomized, // 5
    rampUsersPerSec(10).to(20).during(10.minutes), // 6
    rampUsersPerSec(10).to(20).during(10.minutes).randomized, // 7
    heavisideUsers(1000).during(20.seconds) // 8
  ).protocols(httpProtocol)
)

The building blocks for profile injection the way you want are:

1.	nothingFor(duration): Pause for a given duration.
2.	atOnceUsers(nbUsers): Injects a given number of users at once.
3.	rampUsers(nbUsers) during(duration): Injects a given number of users distributed evenly on a time window of a given duration.
4.	constantUsersPerSec(rate) during(duration): Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
5.	constantUsersPerSec(rate) during(duration) randomized: Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
6.	rampUsersPerSec(rate1) to (rate2) during(duration): Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
7.	rampUsersPerSec(rate1) to(rate2) during(duration) randomized: Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
8.	heavisideUsers(nbUsers) during(duration): Injects a given number of users following a smooth approximation of the heaviside step function stretched to a given duration.

Global Pause configuration
The pauses can be configured on Simulation with a bunch of methods:

1.	disablePauses: disable the pauses for the simulation
2.	constantPauses: the duration of each pause is precisely that specified in the pause(duration) element.
3.	exponentialPauses: the duration of each pause is on average that specified in the pause(duration) element and follow an exponential distribution.
4.	normalPausesWithStdDevDuration(stdDev: Duration): the duration of each pause is on average that specified in the pause(duration) element and follow an normal distribution. stdDev is a Duration.
5.	normalPausesWithPercentageDuration(stdDev: Double): the duration of each pause is on average that specified in the pause(duration) element and follow an normal distribution. stdDev is a percentage of the pause value.
6.	customPauses(custom: Expression[Long]): the pause duration is computed by the provided Expression[Long]. In this case the filled duration is bypassed.
7.	uniformPausesPlusOrMinusPercentage(plusOrMinus: Double) and uniformPausesPlusOrMinusDuration(plusOrMinus: Duration): the duration of each pause is on average that specified in the pause(duration) element and follow a uniform distribution.
