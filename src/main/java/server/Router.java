package server;

import controllers.*;
import model.Negocio.EntityHelper;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.logging.Logger;

import static spark.Spark.*;

public class Router {
  public static void configure() {
    HandlebarsTemplateEngine engineTemplate = new HandlebarsTemplateEngine();

    //DebugScreen.enableDebugScreen();
    Spark.staticFiles.location("public");

    ////////////////////index////////////////////////
    IndexController indexController = new IndexController();

    Spark.get("/",indexController::index,engineTemplate);

    ////////////////////mascotas////////////////////////
    MascotaController mascotaController = new MascotaController();

    path("/form-", () -> {
      Spark.get("sin-chapita", mascotaController::registroSinChapita, engineTemplate);
      Spark.get("con-chapita", mascotaController::obtenerMascota, engineTemplate);

      post("sin-chapita", mascotaController::postSinChapita, engineTemplate);
      post("con-chapita", mascotaController::postConChapita, engineTemplate);
    });

    ////////////////////user////////////////////////
    UserController userController = new UserController();

    path("/duenio", () -> {
      get("/home",userController::homeUser,engineTemplate);
      get("/login",userController::userLogIn,engineTemplate);
      get("/signup",userController::signUp,engineTemplate);

      post("/home",userController::postHomeUser,engineTemplate);
      post("/login",userController::postUserLogIn,engineTemplate);
      post("/signup",userController::postSignUp,engineTemplate);

      path("/mis-mascotas", () -> {
        get("",mascotaController::misMascotas,engineTemplate);
        get("/registro-mascota",mascotaController::registroMascota,engineTemplate);

        post("/registro-mascota",mascotaController::postMascota,engineTemplate);
      });
    });

    ////////////////////admin////////////////////////
    AdminController adminController = new AdminController();

    path("/admin", () -> {
      get("/login",adminController::adminLogin,engineTemplate);
      get("/home",adminController::homeAdmin,engineTemplate);
      get("/form-caracteristica",adminController::caracteristicas,engineTemplate);

      post("/login",adminController::postAdminLogin,engineTemplate);
      post("/home",adminController::postHomeAdmin,engineTemplate);
      post("/form-caracteristica",adminController::postCaracteristicas,engineTemplate);
    });
  }
}
