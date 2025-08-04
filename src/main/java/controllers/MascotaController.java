package controllers;

import model.Asociacion.Ubicacion;
import model.Mascota.Caracteristicas.CaracteristicaMascota;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.Caracteristicas.TipoCaracteristica;
import model.Mascota.DatosMascota;
import model.Mascota.Mascota;
import model.Mascota.MascotaEncontrada.RepositorioMascotaEncontrada;
import model.Mascota.TamanioMascota;
import model.Negocio.EntityHelper;
import model.Negocio.ServiceRescatista;
import model.Persona.Duenio;
import model.Persona.Rescatista;
import model.Usuario.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.*;

import static spark.Spark.after;
import static spark.Spark.before;

public class MascotaController {
  public ModelAndView registroMascota(Request request, Response response) {
    String username = request.cookie("usuario-logueado");
    String tipo = request.cookie("tipoUsuario");

    if(username == null || tipo == null || !tipo.equals("duenio")) {
      response.redirect("/");
    }

    List<TipoCaracteristica> tiposCaracteristicas = RepositorioCaracteristicas.getTipoCaracteristicas().getTipos();
    Map<String, Object> model = new HashMap<>();
    model.put("tipoCaracteristicas",tiposCaracteristicas);
    return new ModelAndView(model,"registro-mascota.hbs");
  }

  public ModelAndView registroSinChapita(Request request, Response response) {
    List<TipoCaracteristica> tiposCaracteristicas = RepositorioCaracteristicas.getTipoCaracteristicas().getTipos();
    Map<String, Object> model = new HashMap<>();
    model.put("tipoCaracteristicas",tiposCaracteristicas);
    return new ModelAndView(model,"form-sin-chapita.hbs");
  }

  public ModelAndView misMascotas(Request request, Response response) {
    String username = request.cookie("usuario-logueado");
    String tipo = request.cookie("tipoUsuario");

    if(username == null || tipo == null || !tipo.equals("duenio")) {
      response.redirect("/");
    }

    Duenio duenio = RepositorioUsuario.getRepositorioUsuario().obtenerDuenio(username);
    List<Mascota> mascotas = duenio.getMascotas();
    Map<String, Object> model = new HashMap<>();
    model.put("mascotas",mascotas);

    return new ModelAndView(model,"mis-mascotas.hbs");
  }

  public ModelAndView postMascota(Request request, Response response) {
    String username = request.cookie("usuario-logueado");

    Duenio duenio = RepositorioUsuario.getRepositorioUsuario().obtenerDuenio(username);

    EntityHelper.getEntityHelper().empezarTransaccion();

    Mascota mascota = generarMascota(request);
    duenio.agregarMascota(mascota);

    EntityHelper.getEntityHelper().finalizarTransaccion();
    response.redirect("/duenio/home");
    return null;
  }

  public ModelAndView postSinChapita(Request request, Response response) {
    EntityHelper.getEntityHelper().empezarTransaccion();

    LocalDate fecha = SparkHelper.obtenerFecha(request.queryParams("fechaEncontrado"));
    Ubicacion ubicacion = SparkHelper.obtenerUbicacion(request);
    Rescatista rescatista = generarRescatista(request);
    DatosMascota datosMascota = generarDatosMascota(request);

    ServiceRescatista.getRescatePatitas().registrarMascotaSinChapita(rescatista,datosMascota,fecha,ubicacion);

    EntityHelper.getEntityHelper().finalizarTransaccion();
    response.redirect("/");
    return null;
  }

  private Mascota generarMascota(Request request) {
    TamanioMascota tamanio = SparkHelper.obtenerTamanioMascota(request);
    List<String> fotos = SparkHelper.obtenerFotos(request);
    Integer edad = SparkHelper.obtenerInt(request.queryParams("edad"));
    List<CaracteristicaMascota> caracteristicas = SparkHelper.obtenerCaracteristicas(request);

    return new Mascota(request.queryParams("nombre"),tamanio,request.queryParams("apodo"),
                       edad,request.queryParams("sexo"),request.queryParams("descripcion"),
                       fotos,request.queryParams("tipo"),caracteristicas);
  }



  private Rescatista generarRescatista(Request request) {
    LocalDate fecha = SparkHelper.obtenerFecha(request.queryParams("fechaNacimiento"));

    return new Rescatista(request.queryParams("nombre"),fecha,request.queryParams("tipoDocumento"),
                          request.queryParams("documento"),Arrays.asList(SparkHelper.generarContacto(request)));
  }

  private DatosMascota generarDatosMascota(Request request) {
    List<CaracteristicaMascota> caracteristicas = SparkHelper.obtenerCaracteristicas(request);
    List<String> fotos = SparkHelper.obtenerFotos(request);
    TamanioMascota tamanio = SparkHelper.obtenerTamanioMascota(request);

    return new DatosMascota(request.queryParams("descripcion"),fotos,request.queryParams("tipo"),
                            request.queryParams("sexo"),tamanio,caracteristicas);
  }

    public ModelAndView obtenerMascota(Request request, Response response) {
      String id = request.queryParams("id");
      if (id != null) {
        response.cookie("idMascota",id);
      } else {
        response.redirect("/");
      }
      return new ModelAndView(null,"form-con-chapita.hbs");
    }

  public ModelAndView postConChapita(Request request, Response response) {
    String id = request.cookie("idMascota");
    EntityHelper.getEntityHelper().empezarTransaccion();

    Mascota mascota = RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().getMascotaPorID(id);
    Rescatista rescatista = generarRescatista(request);
    LocalDate fecha = SparkHelper.obtenerFecha(request.queryParams("fechaEncontrado"));
    Ubicacion ubicacion = SparkHelper.obtenerUbicacion(request);
    List<String> fotos = SparkHelper.obtenerFotos(request);
    String descripcion = request.queryParams("descripcion");

    ServiceRescatista
            .getRescatePatitas()
            .registrarMascotaConChapita(rescatista,fecha,ubicacion,mascota,
                                        descripcion,fotos);

    EntityHelper.getEntityHelper().finalizarTransaccion();
    response.removeCookie("idMascota");
    response.redirect("/");
    return null;
  }
}
