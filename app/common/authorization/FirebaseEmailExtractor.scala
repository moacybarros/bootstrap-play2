package common.authorization

import java.util.Base64

import com.google.inject.Inject
import firebaseAuth.JwtToken
import play.api.{ Configuration, Environment, Logger }

import scala.concurrent.{ ExecutionContext, Future }
import play.api.mvc._
import play.libs.Json

class FirebaseEmailExtractor[A] @Inject()(val parser: BodyParsers.Default, environment: Environment)(
  implicit val executionContext: ExecutionContext,
  configuration: Configuration
) {

  /**
   * Get Email from request
   * Takes the request header "Authorization" and extracts the JWT token,
   * then decodes the token to get the user_Id for a Database lookup of
   * that id through the injected UsersDAO
   * @param request
   * @tparam A
   * @return Option[String]
   */
  def extractEmail[A](request: Request[A]): Option[String] = {
    val optionToken: Option[JwtToken] = extractToken(request.headers.get("Authorization"))
    extractEmailFromToken(optionToken)
  }

  private def extractToken(authorizationHeader: Option[String]): Option[JwtToken] =
    authorizationHeader match {
      case Some(header) if environment.mode.toString() == "Test" =>
        Some(JwtToken(header))
      case Some(header) => {
        header match {
          case x: String if x.startsWith("Bearer") =>
            Some(JwtToken(x.splitAt(7)._2))
          case x => Some(JwtToken(x))
        }
      }
      case None => {
        None
      }
    }

  private def extractEmailFromToken(optionToken: Option[JwtToken]): Option[String] =
    optionToken match {
      case Some(token) if environment.mode.toString() == "Test" =>
        Some(token.content)
      case Some(token) => {
        val tokenCont = token.content.split('.')(1)
        val decoded   = new String(Base64.getDecoder.decode(tokenCont))
        Some(Json.parse(decoded).get("email").asText())
      }
      case None => {
        None
      }
    }

}
