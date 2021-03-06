package common.results.errors

import common.results.Results.{ NotLoggingError, SelfLoggingError }

object Errors {
  case class DatabaseError(message: String, errorClass: String, errorMethod: String, internalErrorMessage: String)
      extends SelfLoggingError(message, 400, errorClass, errorMethod, internalErrorMessage)

  case class BadRequest() extends NotLoggingError()

  case class NotFound() extends NotLoggingError()

  case class Forbidden() extends NotLoggingError()

}
