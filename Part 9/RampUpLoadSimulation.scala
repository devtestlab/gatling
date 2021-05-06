package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import scala.language.postfixOps

class RampUpLoadSimulation extends Simulation {

  /*
  THIS PROGRAM IS FOR:
  1. Use CSV FILE AS SOURCE OF DATA FOR REST CALL WITH HELP OF CSVFEEDER USE IT
  2. THEN WE CAN USE ANY STRATEGIES MENTIONED BELOW FOR NOW CIRCULAR WE ARE USING
  3. THEN FUNCTION IS DEFINED TO LOOP THROUGH CSV FILE FOR 10 TIMES
  4. FINALLY CALL THE FUNCTION TO EXECUTE THE CASE WITH A START OF 10 USERS AND THEN IN NEXT 10 SECONDS
     AND THEN RANDOM COUNT FROM 1 TO 5 FOR NEXT 20 SECONDS AS MORE USERS WILL BE RAMPED UP AS LOAD TO WEBSITE
 */

  val httpConf = http.baseUrl("http://gorest.co.in")
    .header("Authorization", "Bearer dba6e3d77bc25db0972b7dbcf60af6b7fbbff7bf30f3eb1d3e24e5193e165ab6")

  /*
  Gatling provides multiple strategies for the built-in feeders:
  .queue // default behavior: use an Iterator on the underlying sequence
  .random // randomly pick an entry in the sequence
  .shuffle // shuffle entries, then behave like queue
  .circular // go back to the top of the sequence once the end is reached
   */
  val csvFeeder = csv("./src/test/resources/data/accessUsers.csv").circular

  def getAUser():ChainBuilder={
    repeat(1){
      feed(csvFeeder)
        .exec(http("Get one user")
          .get("/public-api/users/${userid}")
          .check(jsonPath("$.data.name").is("${name}"))
          .check(status.in(200,304)))
    }
  }

  val scn = scenario("Ramp Up Get Users with CSV Feeder")
    .exec(getAUser())

  //setup
  setUp(
    scn.inject(
      nothingFor(5),
      constantUsersPerSec(10) during(10), // This will send 10 users for next seconds meaning after this we will have 100 users
      rampUsersPerSec(1) to (5)during(20) // This will send a random users between 1 to 5 for next 20 seconds
    )
  )
    .protocols(httpConf)
}
