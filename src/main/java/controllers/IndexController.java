package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.Set;

public class IndexController {

  public ModelAndView index(Request request, Response response) {
    String tipo = request.cookie("tipoUsuario");
    String username = request.cookie("usuario-logueado");
    
    if(tipo != null && tipo.equals("duenio") && username != null) {
      response.redirect("/duenio/home");
    }

    if(tipo != null && tipo.equals("administrador") && username != null) {
      response.redirect("/admin/home");
    }

    return new ModelAndView(null,"index.hbs");
  }
}
