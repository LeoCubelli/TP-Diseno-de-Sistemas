package controllers;

import model.Negocio.EntityHelper;
import model.Negocio.ServiceRegistroUsuarios;
import model.Persona.Contacto;
import model.Usuario.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Arrays;

public class UserController {
  public ModelAndView homeUser(Request request, Response response) {
    String username = request.cookie("usuario-logueado");
    String tipo = request.cookie("tipoUsuario");

    if(username == null || tipo == null || !tipo.equals("duenio")) {
      response.redirect("/");
    }

    return new ModelAndView(null,"home-user.hbs");
  }

  public ModelAndView postHomeUser(Request request, Response response) {
    response.removeCookie("/","usuario-logueado");
    response.removeCookie("/","tipoUsuario");
    response.redirect("/");
    return null;
  }

  public ModelAndView userLogIn(Request request, Response response) {
    return new ModelAndView(null,"login.hbs");
  }

  public ModelAndView postUserLogIn(Request request, Response response) {
    String usuario = request.queryParams("usuario");
    String password = request.queryParams("password");

    boolean esUsuarioValido = RepositorioUsuario.getRepositorioUsuario().esUsuarioValido(usuario,password,"duenio");

    if(esUsuarioValido) {
      response.cookie("/","usuario-logueado",usuario,3600,false,true);
      response.cookie("/","tipoUsuario","duenio",3600,false,true);
      response.redirect("/duenio/home");
    }

    response.redirect("/duenio/login");

    return null;
  }

  public ModelAndView signUp(Request request, Response response) {
    return new ModelAndView(null,"signup.hbs");
  }

  public ModelAndView postSignUp(Request request, Response response) {
    EntityHelper.getEntityHelper().empezarTransaccion();

    Contacto contacto = SparkHelper.generarContacto(request);
    LocalDate fechaNacimiento = SparkHelper.obtenerFecha(request.queryParams("fechaNacimiento"));

    try {
      ServiceRegistroUsuarios
          .getRegistroUsuarios()
          .registrarDuenio(request.queryParams("usuario"),
              request.queryParams("password"),
              request.queryParams("nombre"),
              fechaNacimiento,
              request.queryParams("tipoDocumento"),
              request.queryParams("documento"),
              Arrays.asList(contacto),
              Arrays.asList());
    } catch (Exception e) {
      EntityHelper.getEntityHelper().finalizarTransaccion();
      response.redirect("/duenio/signup");
    }

    EntityHelper.getEntityHelper().finalizarTransaccion();
    response.redirect("/duenio/login");
    return null;
  }
}




