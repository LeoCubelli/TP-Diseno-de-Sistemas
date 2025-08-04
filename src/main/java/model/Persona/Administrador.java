package model.Persona;

import model.Asociacion.Publicacion.Preguntas.Pregunta;
import model.Asociacion.Publicacion.RepositorioPreguntas;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Usuario.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("administrador")
public class Administrador extends Usuario {
  public Administrador(String usuario, String contrasena) {
    super(usuario, contrasena);
  }

  public Administrador() {
    super();
  }

  public void agregarCaracteristicas(String ... caracteristicas) {
    RepositorioCaracteristicas.getTipoCaracteristicas().agregarTipoCaracteristicas(caracteristicas);
  }

  public void agregarPregunta(Pregunta pregunta) {
    RepositorioPreguntas.getRepositorioPreguntas().agregarPreguntaGeneral(pregunta);
  }
}
