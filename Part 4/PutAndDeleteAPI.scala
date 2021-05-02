package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._


class PutAndDeleteAPI  extends Simulation {

  /*
  THIS PROGRAM IS FOR:
  1. UPDATING A RECORD USING PUT COMMAND
  2. THEN DELETING THE USER USING DELETE COMMAND
   */

  val httpConf = http.baseUrl("http://reqres.in")
    .header("Accept","application/json")
    .header("Content-type","application/json")

  val scn = scenario("PUT and DELETE API")

    // Scenario to Update existing user with PUT
    .exec(http("Put Request")
      .put("/api/users/2")
      .body(RawFileBody("./src/test/resources/payload/UpdateUser.json")).asJson
      .check(status.in(200 to 210)))

    .pause(3)

    // Scenario to delete one user from records
    .exec(http("Deleting user record")
      .delete("/api/users/2")
      .check(status.in(expected = 200 to 204))

    )

  //setup
  setUp(
    scn.inject(
      atOnceUsers(100)
    )
  )
    .protocols(httpConf)
}
