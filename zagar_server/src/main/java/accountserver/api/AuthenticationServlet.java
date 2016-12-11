package accountserver.api;

import info.AuthDataStorage;
import info.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLClientInfoException;

@Path("/auth")
public class AuthenticationServlet {
  private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
  //private AuthDataStorage ts = new AuthDataStorage();
  // curl -i
  //      -X POST
  //      -H "Content-Type: application/x-www-form-urlencoded"
  //      -H "Host: {IP}:8080"
  //      -d "user={}&password={}"
  // "{IP}:8080/auth/register"
  @POST
  @Path("register")
  @Consumes("application/x-www-form-urlencoded")
  @Produces("text/plain")
  public Response register(@FormParam("user") String user,
                           @FormParam("password") String password) {

    if (user == null || password == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    try {
      AuthDataStorage.registerNewUser(user, password);
      log.info("New user '{}' registered", user);
      return Response.ok("User " + user + " registered.").build();
    }
    catch(SQLClientInfoException e){
      log.info("User with name '{}' already exists.", user);
      return Response.status(Response.Status.CONFLICT).build();
    }
    catch(Exception e){
      log.info(e.getMessage());
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
  }

  // curl -X POST
  //      -H "Content-Type: application/x-www-form-urlencoded"
  //      -H "Host: localhost:8080"
  //      -d "user=admin&password=admin"
  // "http://localhost:8080/auth/login"
  @POST
  @Path("login")
  @Consumes("application/x-www-form-urlencoded")
  @Produces("text/plain")
  public Response authenticateUser(@FormParam("user") String user,
                                   @FormParam("password") String password) {

    if (user == null || password == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    try {
      if (!AuthDataStorage.authenticate(user, password)) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      Token token = AuthDataStorage.issueToken(user);
      log.info("User '{}' logged in", user);

      return Response.ok(Long.toString(token.getId())).build();

    } catch (Exception e) {
      log.info("Exception");
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
  }

  //curl -X POST -H
  // "content-Type:application/x-www-form-urlencoded"
  // -H "Authorization: Bearer 1"
  // -H "Host:localhost:8080"
  // "localhost:8080/auth/logout"
  @POST
  @Path("logout")
  @Produces("text/plain")
  @Authorized
  public Response logoutUser(@HeaderParam("Authorization") String authHeader) {
    try {
      Token token = new Token(Long.parseLong(authHeader.substring("Bearer".length()).trim()));
      String userName = AuthDataStorage.logOut(token);
      log.info("User {} logged out", userName);
      return Response.ok("User '"+userName+"' logged out").build();
    } catch (Exception e) {
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
  }

  public static boolean validateToken(@NotNull String rawToken) {
    Long token = Long.parseLong(rawToken);
    if (!AuthDataStorage.tokenExists(token)) {
      return false;
    }
    return true;
  }

}
