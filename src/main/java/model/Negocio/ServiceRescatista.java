package model.Negocio;

import model.Asociacion.RepositorioAsociaciones;
import model.Asociacion.Ubicacion;
import model.Hogar.Hogar;
import model.Mascota.Mascota;
import model.Mascota.DatosMascota;
import model.Mascota.MascotaEncontrada.MascotaConChapita;
import model.Mascota.MascotaEncontrada.MascotaSinChapita;
import model.Asociacion.Publicacion.PublicacionMascotaSinChapita;
import model.Mascota.MascotaEncontrada.RepositorioMascotaEncontrada;
import model.Persona.Duenio;
import model.Persona.Rescatista;
import model.Servicios.Hogares.RepositorioHogares;
import model.Servicios.Mails.MailHandler;
import model.Usuario.RepositorioUsuario;

import java.time.LocalDate;
import java.util.List;

public class ServiceRescatista {
  private static final ServiceRescatista rescatePatitas = new ServiceRescatista();

  private ServiceRescatista() {

  }

  public static ServiceRescatista getRescatePatitas() {
    return rescatePatitas;
  }

  public void registrarMascotaSinChapita(Rescatista rescatista, DatosMascota datosMascota, LocalDate fechaEncontrada, Ubicacion ubicacion) {
    RepositorioAsociaciones.getRepositorioAsociaciones()
        .encontrarAsociacionMasCercana(ubicacion)
        .agregarPublicacion(new PublicacionMascotaSinChapita(new MascotaSinChapita(datosMascota,rescatista,ubicacion,fechaEncontrada)));
  }

  public void registrarMascotaConChapita(Rescatista rescatista, LocalDate fechaEncontrada, Ubicacion ubicacion, Mascota mascota, String descripcion, List<String> fotos){
    MascotaConChapita mascotaConChapita = new MascotaConChapita(fechaEncontrada, rescatista, ubicacion, mascota, descripcion, fotos);
    RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().registrarMascotaEncontrada(mascotaConChapita);
    notificarDuenioMascota(mascota);
  }

  public List<Hogar> encontrarHogares(MascotaSinChapita mascotaSinChapita, int radioCercania) {
    return RepositorioHogares.getRepositorioHogares().getHogaresApropiados(radioCercania,mascotaSinChapita);
  }

  private void notificarDuenioMascota(Mascota mascota){
    Duenio duenio = RepositorioUsuario.getRepositorioUsuario().encontrarDuenio(mascota);
    MailHandler.getMailHandler().enviarMensaje(duenio.getContactos(),"Encontré a tu mascota");
  }
}