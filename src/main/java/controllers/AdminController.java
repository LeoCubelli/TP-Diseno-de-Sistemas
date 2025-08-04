package controllers;

import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.Caracteristicas.TipoCaracteristica;
import model.Negocio.EntityHelper;
import model.Persona.Administrador;
import model.Usuario.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {
  public ModelAndView homeAdmin(Request request, Response response) {
    String username = request.cookie("usuario-logueado");
    String tipo = request.cookie("tipoUsuario");

    if(username == null || !tipo.equals("administrador")) {
      response.redirect("/");
    }

    return new ModelAndView(null,"home-admin.hbs");
  }

  public ModelAndView postHomeAdmin(Request request, Response response) {
    response.removeCookie("/","usuario-logueado");
    response.removeCookie("/","tipoUsuario");
    response.redirect("/");
    return null;
  }

  public ModelAndView adminLogin(Request request, Response response) {
    return new ModelAndView(null,"login-admin.hbs");
  }

  public ModelAndView postAdminLogin(Request request, Response response) {
    String usuario = request.queryParams("usuario");
    String password = request.queryParams("password");

    boolean esUsuarioValido = RepositorioUsuario.getRepositorioUsuario().esUsuarioValido(usuario,password,"administrador");

    if(esUsuarioValido) {
      response.cookie("/","usuario-logueado",usuario,3600,false,true);
      response.cookie("/","tipoUsuario","administrador",3600,false,true);
      response.redirect("/admin/home");
    }

    response.redirect("/admin/login");

    return null;
  }

  public ModelAndView caracteristicas(Request request, Response response) {
    String username = request.cookie("usuario-logueado");
    String tipo = request.cookie("tipoUsuario");

    if(username == null || !tipo.equals("administrador")) {
      response.redirect("/");
    }

    List<TipoCaracteristica> tiposCaracteristicas = RepositorioCaracteristicas.getTipoCaracteristicas().getTipos();
    Map<String, Object> model = new HashMap<>();
    model.put("tipoCaracteristicas",tiposCaracteristicas);

    return new ModelAndView(model,"form-caracteristica-admin.hbs");
  }

  public ModelAndView postCaracteristicas(Request request, Response response) {
    String tipoCaracteristica = request.queryParams("tipoCaracteristica");
    String username = request.cookie("usuario-logueado");

    Administrador admin = RepositorioUsuario.getRepositorioUsuario().obtenerAdmin(username);

    EntityHelper.getEntityHelper().empezarTransaccion();

    admin.agregarCaracteristicas(tipoCaracteristica);

    EntityHelper.getEntityHelper().finalizarTransaccion();

    response.redirect("/admin/form-caracteristica");
    return null;
  }
}
