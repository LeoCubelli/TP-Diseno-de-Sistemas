package model.Negocio;

import model.Asociacion.Asociacion;
import model.Asociacion.Publicacion.PublicacionMascotaDeseada;
import model.Asociacion.Publicacion.PublicacionMascotaParaAdopcion;
import model.Asociacion.RepositorioAsociaciones;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//crontab -e
//0 0 * * 1 java -jar /path/EnvioRecomendaciones.jar

public class EnvioRecomendaciones {
  private Timer timer;

  private static final EnvioRecomendaciones envioRecomendaciones = new EnvioRecomendaciones();

  private EnvioRecomendaciones() {
    this.timer = new Timer();
  }

  public void activarEnvioDeRecomendaciones() {
    timer.schedule(new TimerTask() {
      public void run() {
        //enviarRecomendaciones();
        System.out.println(LocalDateTime.now() + ": Recomendaciones enviadas satisfactoramiente\n");
      }
    },0, TimeUnit.MINUTES.toMillis(1));
  }

  public static EnvioRecomendaciones getEnvioRecomendaciones() {
    return envioRecomendaciones;
  }

  public void enviarRecomendaciones() {
    List<Asociacion> asociaciones = RepositorioAsociaciones.getRepositorioAsociaciones().getAsociaciones();
    asociaciones.forEach(a -> enviarRecomendaciones(a));
  }

  private void enviarRecomendaciones(Asociacion asociacion) {
    List<PublicacionMascotaDeseada> publicacionesDeAdopcion = asociacion.getPublicacionesMascotaDeseada();
    List<PublicacionMascotaParaAdopcion> mascotasParaAdopcion = asociacion.getPublicacionesParaAdopcion();
    publicacionesDeAdopcion.forEach(p -> p.contactarInteresado(generarMensaje(mascotasPosibles(p,mascotasParaAdopcion))));
  }

  public List<PublicacionMascotaParaAdopcion> mascotasPosibles(PublicacionMascotaDeseada publicacionDeAdopcion, List<PublicacionMascotaParaAdopcion> mascotasParaAdopcion) {
    return mascotasParaAdopcion.stream().filter(publicacion ->publicacion.getEsPublica() && publicacionDeAdopcion.mascotaSatisfacePreferencias(publicacion.getRespuestas())).collect(Collectors.toList());
  }

  private String generarMensaje(List<PublicacionMascotaParaAdopcion> mascotasPosibles) {
    StringBuffer finalTemp = new StringBuffer("");
    mascotasPosibles.forEach(p -> finalTemp.append(p + "\n\n"));
    return finalTemp.toString();
  }
}