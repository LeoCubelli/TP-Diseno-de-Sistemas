package model.Asociacion.Publicacion;

import model.Asociacion.Publicacion.Preguntas.Respuesta;
import model.Entidad.EntidadPersistente;
import model.Persona.Contacto;
import model.Servicios.Mails.MailHandler;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publicacion_mascota_deseada")
public class PublicacionMascotaDeseada extends EntidadPersistente {
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "publicacion_mascota_deseada_id", referencedColumnName = "id")
  private List<Respuesta> respuestas;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "contacto_id", referencedColumnName = "id")
  private Contacto contacto;

  @Column
  private boolean esPublica = true;

  public PublicacionMascotaDeseada(List<Respuesta> respuestas, Contacto contacto) {
    this.respuestas = respuestas;
    this.contacto = contacto;
  }

  public PublicacionMascotaDeseada() {

  }

  public boolean mascotaSatisfacePreferencias(List<Respuesta> respuestasMascota) {
    double cantRespuestas = respuestasMascota.size();
    double cantRespuestasSatisfechas = respuestas
                                      .stream()
                                      .filter(p -> p.satisfechaEn(respuestasMascota))
                                      .count();
    return cantRespuestasSatisfechas/cantRespuestas >= 0.5; //nosotros decidimos que tenga que cumplir ese porcentaje, para que no sea que solo con una respuesta la recomiende
  }

  //Asumimos que la baja implica ocultar la visibilidad de la publicación
  public void darDeBaja() {
    esPublica = false;
  }

  public void contactarInteresado(String mensaje){
    if(esPublica) {
      MailHandler.getMailHandler().enviarMensaje(contacto.getEmail(),mensaje);
    }
  }

  public boolean getEsPublica() {
    return esPublica;
  }
}
