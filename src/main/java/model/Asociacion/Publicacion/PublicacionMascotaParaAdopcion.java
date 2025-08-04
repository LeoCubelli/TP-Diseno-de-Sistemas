package model.Asociacion.Publicacion;

import model.Asociacion.Publicacion.Preguntas.Respuesta;
import model.Entidad.EntidadPersistente;
import model.Persona.Contacto;
import model.Servicios.Mails.MailHandler;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publicacion_mascota_adopcion")
public class PublicacionMascotaParaAdopcion extends EntidadPersistente {
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "contacto_id", referencedColumnName = "id")
  private Contacto contactoDuenio;
  @Column
  private boolean esPublica = true;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "publicacion_mascota_adopcion_id", referencedColumnName = "id")
  private List<Respuesta> respuestas;

  public PublicacionMascotaParaAdopcion(Contacto contactoDuenio, List<Respuesta> respuestas) {
    this.contactoDuenio = contactoDuenio;
    this.respuestas = respuestas;
  }

  public PublicacionMascotaParaAdopcion() {

  }

  //Se le informa que alguien está dispuesto a adoptar a la mascota.
  public void contactarDuenioParaAdoptar(String mensaje){ //todo verificar issue 38
    if(esPublica) {
      MailHandler.getMailHandler().enviarMensaje(contactoDuenio.getEmail(),mensaje);
    } else
      throw new RuntimeException("La publicacion ya no esta disponible");
  }

  public String toString(){
    StringBuffer finalTemp = new StringBuffer(contactoDuenio.getNombre() + " " + contactoDuenio.getApellido() + " " + contactoDuenio.getEmail() + "\n");
    respuestas.forEach(p -> finalTemp.append(p.getPregunta() + ": " + p.getRespuesta() + "\n"));
    return finalTemp.toString();
  }

  public void darDeBaja(){
    esPublica = false;
  }

  public List<Respuesta> getRespuestas() {
   return respuestas;
  }

  public boolean getEsPublica() {
    return esPublica;
  }
}