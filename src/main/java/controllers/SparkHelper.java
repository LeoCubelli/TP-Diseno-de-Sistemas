package controllers;

import model.Asociacion.Ubicacion;
import model.Mascota.Caracteristicas.CaracteristicaMascota;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.TamanioMascota;
import model.Persona.Contacto;
import spark.Request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SparkHelper {
  public static List<CaracteristicaMascota> obtenerCaracteristicas(Request request) {
    List<String> tipoCaracteristicas = request.queryParams().stream()
                                                            .filter(s -> s.startsWith("caracteristica-"))
                                                            .collect(Collectors.toList());

    Map<String,String> parametros = tipoCaracteristicas.stream().collect(Collectors.toMap(p -> p.split("caracteristica-")[1],p -> request.queryParams(p)));
    return RepositorioCaracteristicas.getTipoCaracteristicas().obtenerCaracteristicasMascota(parametros);
  }

  public static LocalDate obtenerFecha(String fecha) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(fecha, formatter);
  }

  public static Ubicacion obtenerUbicacion(Request request) {
    String[] ubicacion = request.queryParams("ubicacion").split(",");
    return new Ubicacion(request.queryParams("direccion"),
                         Double.parseDouble(ubicacion[0]),
                         Double.parseDouble(ubicacion[1]));
  }

  public static List<String> obtenerFotos(Request request) {
    return Arrays.asList(request.queryParams("fotos").split(","));
  }

  public static TamanioMascota obtenerTamanioMascota(Request request) {
    return TamanioMascota.values()[Integer.parseInt(request.queryParams("tamanio"))];
  }

  public static Integer obtenerInt(String valor) {
    return Integer.parseInt(valor);
  }

  public static Contacto generarContacto(Request request) {
    Integer telefono = SparkHelper.obtenerInt(request.queryParams("telefono"));

    return new Contacto(request.queryParams("nombreContacto"),
            request.queryParams("apellidoContacto"),
            telefono,request.queryParams("email"));
  }
}
