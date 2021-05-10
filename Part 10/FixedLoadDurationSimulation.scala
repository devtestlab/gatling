package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class FixedLoadDurationSimulation extends Simulation {

  /*
  THIS PROGRAM IS USING THREE FUNCTIONS:
  1. ALL FUNCTIONS ARE DEFINED SEPARATELY TO GET ALL USERS THEN ONE USER AND FINALLY ADD ONE USER
  2. ADDITION OF USER WILL BE DONE WITH JSON FILE AS PER SPECIFIED FILE LOCATION
  3. FUNCTIONS ALSO CONTAINS REPEAT METHOD THAT WILL ITERATE THE FUNCTION AS PER SPECIFIED COUNT
  4. IN SCENARIOS WE'VE TO CALL FUNCTIONS INCLUDING PAUSE AS PER REQUIREMENT INSTEAD OF WRITE CODE AGAIN
  5. AND FINALLY SETUP THE CALL AS PER USER COUNT
 */

  //http configuration
  val httpConf = http.proxy(Proxy("localhost",8888))
    .baseUrl("http://reqres.in")
    .header("Accept","application/json")
    .header("content-type","application/json")

  // Function to get all users from URL and repeat this task 2 times
  def getAllUsers(): ChainBuilder ={
    repeat(2) {
      exec(http("Get all Users")
        .get("/api/users?page=2")
        .check(status.in(expected = 200 to 201)))
    }
  }

  // Function to get one user from URL and repeat this task 1 time
  def getAUser(): ChainBuilder = {
    repeat(2) {
      exec(http("Get Single Users")
        .get("/api/users/2")
        .check(status.in(expected = 200 to 201)))
    }
  }

  // Function to add one user using POST and JSON file for user info and repeat this task 2 times
  def addNewUser():ChainBuilder = {
    repeat(2){
      exec(http("Add New User")
        .post("/api/users")
        .body(RawFileBody("./src/test/resources/payload/addNewUser.json")).asJson
        .check(status.in(expected = 200 to 201)))
    }
  }

  val scn = scenario("Fixed Load Duration")
    .forever() {
      exec(getAllUsers()) //getAllUsers Function call
        .pause(2)
        .exec(getAUser()) //getAUser Function call
        .pause(2)
        .exec(addNewUser()) //addNewUser Function call
    }


  //setup
  setUp(
    scn.inject(
      nothingFor(5), // It will pause the application for 5 seconds
      atOnceUsers(10), // Start app with 10 users at once
      rampUsers(50).during(30) // start loading 50 users for next 30 seconds
    ).protocols(httpConf)
  ).maxDuration(60)  // This is important as it will set the maximum duration where above mentioned request has to be finished with in this time.

}