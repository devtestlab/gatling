package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PauseInScenarios extends Simulation{

    /*
      THIS PROGRAM IS FOR:
      1. GETTING LIST OF ALL USERS FROM THE RECORDS
      2. THEN GETTING THE LIST OF SINGLE USER FROM REOCRDS
      3. AND FINALLY TARGETING A NON EXISTING USER IN RECORDS
     */

  val httpConf = http.baseUrl("http://reqres.in")
    .header("Accept","application/json")

  val scn = scenario("Pause Testing")
    // First Scenario for checking all users
    .exec(http("Get All Users")
      .get("/api/users?page=2")
      .check(status.is(expected = 200)))

    .pause(3)

    // Second Scenario for checking user 2
    .exec(http("Get Single User")
      .get("/api/users/2")
      .check(status.in(expected = 200 to 210)))

    .pause(1,10)

    // Third Scenario for checking User Not found case
    .exec(http("User Not Found")
      .get("/api/users/23")
      .check(status.not(expected = 400),status.not(expected = 500)))

    .pause(3000.milliseconds)

  //setup
  setUp(
    scn.inject(
      atOnceUsers(1)
    )
  )
    .protocols(httpConf)
}