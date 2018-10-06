package petclinic

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PetsSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:9966/petclinic/api") // <1>
    .acceptHeader("application/json") // <2>
    .acceptCharsetHeader("UTF-8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Pets Scenario") // <3>
    .exec(http("List pets") // <4>
      .get("/pets")
        .check( status.is(200))) // <5>

  setUp(
    scn.inject(constantUsersPerSec(800) during(30 seconds)) // <6>
    .protocols(httpProtocol)
  )
}
