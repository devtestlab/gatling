package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FirstTestApi extends Simulation {

  /*
    THIS PROGRAM IS FOR:
    1. ADD NEW RECORD OF A USER USING JSON FILE
   */

  val httpConf = http.baseUrl("http://reqres.in")
    .header("Accept","application/json")

  val scn = scenario("Pause Testing")
    // First Scenario for checking all users
    .exec(http("Get All Users")
      .get("/api/users?page=2")
      .check(status.is(expected = 200)))

  //setup
  setUp(
    scn.inject(
      atOnceUsers(1)
    )
  )
    .protocols(httpConf)
}
