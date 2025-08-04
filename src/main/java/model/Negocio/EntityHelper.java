package model.Negocio;

import model.Asociacion.Ubicacion;
import model.Persona.Contacto;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;

public class EntityHelper extends AbstractPersistenceTest implements WithGlobalEntityManager {
  private static final EntityHelper entityHelper = new EntityHelper();

  private EntityHelper() {

  }

  public static EntityHelper getEntityHelper() {
    return entityHelper;
  }

  public EntityManager getEntityManager() {
    return entityManager();
  }

  public void empezarTransaccion() {
    entityManager().getTransaction().begin();
  }

  public void finalizarTransaccion() {
    entityManager().getTransaction().commit();
    entityManager().close();
  }

  public void actualizar() {
    entityManager().flush();
  }

  public void borrar(Object obj) {
    entityManager().remove(obj);
  }

  public void persistir(Object obj) {
    entityManager().persist(obj);
  }

  public void iniciarDB() {
    withTransaction(() -> {});
  }

  public void inicializador() {
    empezarTransaccion();
    WorkspaceAsociacion.getRegistroAsociaciones().registrarAsociacion(new Ubicacion("Calle falsta", 2, 5));
    Contacto contactoLucas = new Contacto("Juan", "perez", 444, "sdf@gmail.com");
    ServiceRegistroUsuarios.getRegistroUsuarios().registrarDuenio("capriLuc", "moti123", "Marcos", LocalDate.of(2000, 2, 12), "dni", "424444444", Arrays.asList(contactoLucas), Arrays.asList());
    ServiceRegistroUsuarios.getRegistroUsuarios().registrarAdministrador("marcos", "marcos2");
    finalizarTransaccion();
  }
}
