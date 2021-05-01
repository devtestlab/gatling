package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class AddUserWithJSON extends Simulation{

  //http configuration
  val httpConf = http.baseUrl("http://reqres.in")
    .header("Accept","application/json")
    .header("content-type","application/json")

  //scenario
    val scn = scenario("add user")
      // Adding one user using JSON file addUser.json
      .exec(http("Add User")
        .post("/api/users")
        .body(RawFileBody("./src/test/resources/payload/addUser.json")).asJson
        .header("content-type","application/json")
        .check(status is 200))

      .pause(3)

      // Get user using get request
      .exec(http("Get User")
        .get("/api/users/2")
        .check(status is 200))

      .pause(3)

      // Get list of all users
      .exec(http("Get All User")
        .get("/api/users?page=2")
        .check(status is 200))

  //setup
  setUp(
    scn.inject(
      atOnceUsers(100)
    )
  )
    .protocols(httpConf)
}
