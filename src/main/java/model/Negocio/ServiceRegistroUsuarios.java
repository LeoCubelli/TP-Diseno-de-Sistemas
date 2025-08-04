package model.Negocio;

import model.Mascota.Mascota;
import model.Persona.Administrador;
import model.Persona.Contacto;
import model.Persona.Duenio;
import model.Persona.Voluntario;
import model.Usuario.RepositorioUsuario;

import java.time.LocalDate;
import java.util.List;

public class ServiceRegistroUsuarios {

  private static final ServiceRegistroUsuarios registroUsuarios = new ServiceRegistroUsuarios();

  private ServiceRegistroUsuarios() {

  }

  public static ServiceRegistroUsuarios getRegistroUsuarios() {
    return registroUsuarios;
  }

  public Duenio registrarDuenio(String username, String contrasenia, String nombre, LocalDate fechaDeNacimiento, String tipoDocumento, String documento, List<Contacto> contactos, List<Mascota> mascotas) {
    Duenio duenio = new Duenio(username,contrasenia,nombre,fechaDeNacimiento,tipoDocumento,documento,contactos,mascotas);
    RepositorioUsuario.getRepositorioUsuario().registrarDuenio(duenio);
    return duenio;
  }

  public Administrador registrarAdministrador(String usuario, String contrasena) {
    Administrador admin = new Administrador(usuario,contrasena);
    RepositorioUsuario.getRepositorioUsuario().registrarAdmin(admin);
    return admin;
  }

  public Voluntario registrarVoluntario(String usuario, String contrasena) {
    Voluntario voluntario = new Voluntario(usuario, contrasena);
    RepositorioUsuario.getRepositorioUsuario().registrarVoluntario(voluntario);
    return voluntario;
  }
}
