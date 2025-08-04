package model.Asociacion.Publicacion;

import model.Asociacion.Publicacion.Preguntas.Pregunta;
import model.Asociacion.RepositorioAsociaciones;
import model.Negocio.EntityHelper;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;
import java.util.stream.Collectors;

//Este repositorio es de preguntas generales
public class RepositorioPreguntas {
  private static final RepositorioPreguntas repositorioPreguntas = new RepositorioPreguntas();
  //private static final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
  //private List<Pregunta> preguntas;

  public void clear() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    getPreguntas().forEach(p -> EntityHelper.getEntityHelper().getEntityManager().remove(p));
    EntityHelper.getEntityHelper().finalizarTransaccion();
  }

  public static RepositorioPreguntas getRepositorioPreguntas() {
    return repositorioPreguntas;
  }

  private RepositorioPreguntas() {
    //preguntas = new ArrayList<>();
  }

  //Al agregar una pregunta se agrega en todas las asociaciones YA EXISTENTES
  public void agregarPreguntaGeneral(Pregunta pregunta) {
    if(!pregunta.esGeneral()){
      throw new RuntimeException("La pregunta no es general! Intente nuevamente");
    }

    RepositorioAsociaciones.getRepositorioAsociaciones()
                           .getAsociaciones()
                           .forEach(a -> a.agregarPregunta(pregunta));

    EntityHelper.getEntityHelper().actualizar();
  }

  public List<Pregunta> getPreguntasGenerales() {
    return getPreguntas().stream().filter(Pregunta::esGeneral).collect(Collectors.toList());
  }

  public List<Pregunta> getPreguntas() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from Pregunta").getResultList();
  }

}