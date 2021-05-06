package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseAndExtractedData extends Simulation{

  /*
    THIS PROGRAM IS FOR:
    1. SEARCH FOR ALL USERS USING REST CALL
    2. THEN PIC ONE ID FROM RESULT JSON DATA
    3. SAVE THAT USER ID IN ONE VARIABLE AND THEN USE SAME VARIABLE TO GET DATA FOR THAT USER ID
   */

  val httpConf = http.baseUrl("http://gorest.co.in")
    .header("Authorization", "Bearer dba6e3d77bc25db0972b7dbcf60af6b7fbbff7bf30f3eb1d3e24e5193e165ab6")

  val scn = scenario("Getting user list")

    // First request to get all users from web and then pic one user from json record
    .exec(http("Getting All Users")
    .get("/public-api/users")
      // picking one user as $.data[2].id and save in userID variable
    .check(jsonPath("$.data[2].id").saveAs("userID")))

    .pause(3 )

    .exec(http("Get Single User")
      // using userID variable to get records
      .get("/public-api/users/${userID}")
      // Data values in this code could be different to better to go to baseurl and check updated data and use that to check
      .check(jsonPath("$.data.id").is("47"))
      .check(jsonPath("$.data.name").is("sharath"))
      .check(jsonPath("$.data.email").is("reddy_sheela@little.io"))
      .check(jsonPath("$.data.gender").is("Female"))
      .check(jsonPath("$.data.status").is("Active"))
    )

  //setup
  setUp(
    scn.inject(
      atOnceUsers(1)
    )
  )
    .protocols(httpConf)
}
