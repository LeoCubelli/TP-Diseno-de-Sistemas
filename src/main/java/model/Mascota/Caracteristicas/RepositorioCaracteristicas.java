package model.Mascota.Caracteristicas;

import model.Negocio.EntityHelper;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import spark.Request;

import java.util.*;
import java.util.stream.Collectors;

public class RepositorioCaracteristicas { // que sea el creador de caracteristicas del admin y que las caracteristicas para la mascota venga de una clase distinta
  private static final RepositorioCaracteristicas tiposCaracteristicas = new RepositorioCaracteristicas();
  //private List<String> tipos;

  public void agregarTipoCaracteristicas(String ... tipoCaracteristicas){
    Arrays.stream(tipoCaracteristicas).forEach(c -> EntityHelper.getEntityHelper().getEntityManager().persist(new TipoCaracteristica(c)));
  }

  public void clear() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    getTipos().forEach(a -> EntityHelper.getEntityHelper().getEntityManager().remove(a));
    EntityHelper.getEntityHelper().finalizarTransaccion();
  }

  public List<String> getTiposCaracteristicas() {
    return getTipos().stream().map(TipoCaracteristica::getTipoCaracteristica).collect(Collectors.toList());
  }


  public List<TipoCaracteristica> getTipos() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from TipoCaracteristica").getResultList();
  }

  public List<CaracteristicaMascota> obtenerCaracteristicasMascota(Map<String,String> caracteristicas) {
    return caracteristicas.keySet().stream().filter(p -> !caracteristicas.get(p).equals(""))
                                   .map(p -> new CaracteristicaMascota(p, caracteristicas.get(p)))
                                   .collect(Collectors.toList());
  }

  private RepositorioCaracteristicas(){

  }

  public boolean existeElTipo(String tipo) {
    return getTiposCaracteristicas().contains(tipo);
  }

  public static RepositorioCaracteristicas getTipoCaracteristicas(){
    return tiposCaracteristicas;
  }

}
