package model.Asociacion;

import model.Negocio.EntityHelper;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.*;

public class RepositorioAsociaciones {
  private static final RepositorioAsociaciones repositorioAsociones = new RepositorioAsociaciones();
  //private List<model.Asociacion> asociaciones; //tiene que ser un repo de mascotas para el sistema en general

  public void clear() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    getAsociaciones().forEach(a -> EntityHelper.getEntityHelper().getEntityManager().remove(a));
    EntityHelper.getEntityHelper().finalizarTransaccion();
  }

  public static RepositorioAsociaciones getRepositorioAsociaciones () {
    return repositorioAsociones;
  }

  public List<Asociacion> getAsociaciones() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from Asociacion").getResultList();
  }

  public void agregarAsociacion(Asociacion ... asociaciones) {
    Arrays.stream(asociaciones).forEach(a -> EntityHelper.getEntityHelper().getEntityManager().persist(a));
  }

  private RepositorioAsociaciones () {
    //this.asociaciones = new ArrayList<>();
  }

  public Asociacion encontrarAsociacionMasCercana(Ubicacion otraCoordenada) {
    String query = "SELECT a.* FROM asociacion as a JOIN (SELECT id FROM asociacion ORDER BY " +
                   "ST_Distance_Sphere(point(:lat,:long),point(latitud,longitud)) LIMIT 1) as a1 " +
                   "ON a.id = a1.id";

    return (Asociacion) EntityHelper.getEntityHelper().getEntityManager().createNativeQuery(query,Asociacion.class)
                        .setParameter("lat",otraCoordenada.getLatitud())
                        .setParameter("long",otraCoordenada.getLongitud())
                        .getSingleResult();
  }
}