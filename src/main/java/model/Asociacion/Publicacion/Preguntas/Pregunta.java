package model.Asociacion.Publicacion.Preguntas;

import model.Entidad.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "pregunta")
public class Pregunta extends EntidadPersistente {
  @Column
  private String pregunta;
  @Enumerated(EnumType.STRING)
  private TipoPregunta tipoPregunta;

  public Pregunta(String pregunta, TipoPregunta tipoPregunta) {
    this.pregunta = pregunta;
    this.tipoPregunta = tipoPregunta;
  }

  public Pregunta(){

  }

  public String getPregunta() {
    return pregunta;
  }

  public boolean esGeneral() {
    return tipoPregunta.equals(TipoPregunta.GENERAL);
  }

  public boolean getEsObligatoria() {
    return tipoPregunta.equals(TipoPregunta.OBLIGATORIA) || tipoPregunta.equals(TipoPregunta.GENERAL);
  }

  public boolean coincidePregunta(Pregunta pregunta) {
    return this.pregunta.equals(pregunta.getPregunta());
  }
}
