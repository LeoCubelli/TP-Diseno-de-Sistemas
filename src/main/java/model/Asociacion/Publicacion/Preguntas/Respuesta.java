package model.Asociacion.Publicacion.Preguntas;

import model.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "respuesta")
public class Respuesta extends EntidadPersistente {
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Pregunta pregunta;
  @Column
  private String respuesta;

  public Respuesta(Pregunta pregunta, String respuesta) {
    this.pregunta = pregunta;
    this.respuesta = respuesta;
  }

  public Respuesta() {

  }

  public String getRespuesta() {
    return respuesta;
  }

  public String getPregunta() {
    return pregunta.getPregunta();
  }

  public boolean coincideRespuesta(Respuesta respuesta) {
    return this.respuesta.equals(respuesta.getRespuesta());
  }

  public boolean coincidePregunta(Respuesta respuesta) {
    return getPregunta().equals(respuesta.getPregunta());
  }

  public boolean satisfechaEn(List<Respuesta> respuestas) {
    //preguntas.stream().filter(p->p.getPregunta().equals(preguntaABuscar)).findFirst().get();
    return respuestas.stream().anyMatch(p -> coincidePregunta(p) && coincideRespuesta(p));
  }
}

