package model.Usuario;
import model.Asociacion.Asociacion;
import model.Mascota.Mascota;
import model.Negocio.EntityHelper;
import model.Persona.Duenio;
import model.Persona.Administrador;
import model.Persona.Voluntario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class RepositorioUsuario {
  private static final RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
  
  public void clear() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    remove(getDuenios());
    remove(getVoluntarios());
    remove(getAdmins());
    EntityHelper.getEntityHelper().finalizarTransaccion();
  }

  private <T> void remove(List<T> usuarios) {
    usuarios.forEach(u -> EntityHelper.getEntityHelper().getEntityManager().remove(u));
  }

  public static RepositorioUsuario getRepositorioUsuario() {
    return repositorioUsuario;
  }

  public void registrarDuenio(Duenio duenio) {
    EntityHelper.getEntityHelper().persistir(duenio);
  }

  public void registrarAdmin(Administrador admin) {
    EntityHelper.getEntityHelper().persistir(admin);
  }

  public void registrarVoluntario(Voluntario voluntario) {
    EntityHelper.getEntityHelper().persistir(voluntario);
  }

  public List<Duenio> getDuenios() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from Duenio").getResultList();
  }

  public List<Administrador> getAdmins() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from Administrador").getResultList();
  }

  public List<Voluntario> getVoluntarios() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from Voluntario").getResultList();
  }

  private RepositorioUsuario() {

  }

  public Administrador obtenerAdmin(String username) {
    return getAdmins().stream().filter(a -> a.getNombreUsuario().equals(username))
                               .collect(Collectors.toList()).get(0);
  }

  public Duenio obtenerDuenio(String username) {
    return getDuenios().stream().filter(a -> a.getNombreUsuario().equals(username))
                                .collect(Collectors.toList()).get(0);
  }

  public boolean esUsuarioValido(String username, String password, String tipo) {
    String query = "SELECT * FROM Usuario WHERE nombreUsuario=:username AND contrasenia=:password AND tipo_usuario=:tipo";

    return EntityHelper.getEntityHelper().getEntityManager().createNativeQuery(query,Usuario.class)
                          .setParameter("username",username)
                          .setParameter("password",password)
                          .setParameter("tipo",tipo)
                          .getResultList().size() > 0;
  }

  public Duenio encontrarDuenio(Mascota mascota) {
    String query = "select * from Usuario where id in (select duenio_id from mascota where id=:idMascota)";

    return (Duenio) EntityHelper.getEntityHelper().getEntityManager().createNativeQuery(query,Duenio.class)
                                   .setParameter("idMascota",mascota.getId())
                                   .getSingleResult();
  }

  public boolean existeNombreUsuario(String nombreUsuario) {
    return usuarios().stream().map(Usuario::getNombreUsuario).anyMatch(user -> user.equals(nombreUsuario));
  }

  public List<Usuario> usuarios() {
    return EntityHelper.getEntityHelper().getEntityManager().createQuery("from Usuario").getResultList();
  }
}