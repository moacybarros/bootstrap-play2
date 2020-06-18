name := "firebase-auth"

// JWT
val firebaseAdmin = "com.google.firebase" % "firebase-admin" % "6.12.2"
val nimbusJoseJWT = "com.nimbusds" % "nimbus-jose-jwt" % "5.3"

libraryDependencies ++= Seq(
  firebaseAdmin,
  nimbusJoseJWT
)