package model.Mascota.MascotaEncontrada;

import model.Mascota.Mascota;
import model.Negocio.EntityHelper;
import model.Usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioMascotaEncontrada {
  private static final RepositorioMascotaEncontrada repositorioMascotaEncontrada = new RepositorioMascotaEncontrada();
  //private List<MascotaConChapita> mascotasEncontradas; //tiene que ser un repo de mascotas para el sistema en general

  public void clear() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    getMascotasEncontradas().forEach(a -> EntityHelper.getEntityHelper().getEntityManager().remove(a));
    EntityHelper.getEntityHelper().finalizarTransaccion();
  }

  public static RepositorioMascotaEncontrada getRepositorioMascotaEncontrada() {
    return repositorioMascotaEncontrada;
  }

  public List<MascotaConChapita> getMascotasEncontradas() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from MascotaConChapita").getResultList();
  }

  public Mascota getMascotaPorID(String id) {
    String query = "SELECT * FROM mascota WHERE id=:idMascota";

    return (Mascota) EntityHelper.getEntityHelper().getEntityManager().createNativeQuery(query,Mascota.class)
            .setParameter("idMascota",id)
            .getSingleResult();
  }

  private RepositorioMascotaEncontrada() {
    //this.mascotasEncontradas = new ArrayList<>();
  }

  public void registrarMascotaEncontrada(MascotaConChapita mascotaEncontrada) {
    EntityHelper.getEntityHelper().persistir(mascotaEncontrada);
  }

  public List<MascotaConChapita> ultimasMascotasEncontradas() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from MascotaConChapita " +
            "where datediff(curdate(),fecha) between 0 and 10")
        .getResultList();
  }
}
