package model.Negocio;

import model.Asociacion.Asociacion;
import model.Asociacion.Publicacion.Preguntas.Pregunta;
import model.Asociacion.Publicacion.RepositorioPreguntas;
import model.Asociacion.Ubicacion;
import model.Asociacion.RepositorioAsociaciones;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class WorkspaceAsociacion {
  private static final WorkspaceAsociacion registroAsociaciones = new WorkspaceAsociacion();

  private WorkspaceAsociacion() {

  }

  public static WorkspaceAsociacion getRegistroAsociaciones() {
    return registroAsociaciones;
  }

  //Al registrar una asociacion se le asocian todas las preguntas generales.
  public Asociacion registrarAsociacion(Ubicacion ubicacion) {
    Asociacion asociacion = new Asociacion(ubicacion);
    List<Pregunta> preguntasGenerales = RepositorioPreguntas.getRepositorioPreguntas().getPreguntasGenerales();
    preguntasGenerales.forEach(asociacion::agregarPregunta);
    RepositorioAsociaciones.getRepositorioAsociaciones().agregarAsociacion(asociacion);
    return asociacion;
  }

  public void quitarPreguntaGeneral(Pregunta pregunta) {
    if (pregunta.esGeneral()) {
      RepositorioAsociaciones.getRepositorioAsociaciones().getAsociaciones().forEach(a -> a.quitarPregunta(pregunta));
      EntityHelper.getEntityHelper().actualizar();
    } else {
      throw new RuntimeException("La pregunta debe ser GENERAL");
    }

    EntityHelper.getEntityHelper().borrar(pregunta);
  }

  public void quitarPregunta(Asociacion asociacion, Pregunta pregunta) {
    if (!pregunta.esGeneral()) {
      asociacion.quitarPregunta(pregunta);
      EntityHelper.getEntityHelper().borrar(pregunta);
    } else {
      throw new RuntimeException("La pregunta no debe ser GENERAL");
    }
  }
}
