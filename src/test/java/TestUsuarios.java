import model.Asociacion.Publicacion.RepositorioPreguntas;
import model.Asociacion.RepositorioAsociaciones;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.MascotaEncontrada.RepositorioMascotaEncontrada;
import model.Negocio.EntityHelper;
import model.Negocio.ServiceRegistroUsuarios;
import model.Persona.Contacto;
import model.Persona.Duenio;
import model.Usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import model.Usuario.*;
import model.Mascota.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestUsuarios {
  @BeforeEach
  void init() {
    RepositorioAsociaciones.getRepositorioAsociaciones().clear();
    RepositorioUsuario.getRepositorioUsuario().clear();
    RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().clear();
    RepositorioCaracteristicas.getTipoCaracteristicas().clear();
    RepositorioPreguntas.getRepositorioPreguntas().clear();
  }

  @Test
  public void testCreacionDuenioConContraseniaComun() {
    assertThrows(RuntimeException.class, () -> new Usuario("marquiXD", "123456"));
  }

  @Test
  public void testCreacionUsuarioConContraseniaFuerte() {
    assertDoesNotThrow(() -> new Usuario("marquiXD", "holaholasososososossoosDDSDDSDSDSDSDSaSD123456"));
  }

  @Test
  public void testDuenioCreaUsuarioCorrectamente() {
    Contacto juan = new Contacto("Juan", "perez", 444, "sdf@gmail.com");
    Duenio lucas = new Duenio("capriLucasss", "sososososolokeoek", "Marcos", LocalDate.of(2000, 2, 12), "dni", "424444444", Arrays.asList(juan), Arrays.asList());
    assertEquals("capriLucasss", lucas.getNombreUsuario());
  }

  @Test
  public void testEncontrarDuenio() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    Mascota scooby = new Mascota("Scooby", TamanioMascota.MEDIANA, "DOo", 5, "Masculino", "Perro investigador", Arrays.asList("Una"), "perros", Arrays.asList());
    Contacto contactoLucas = new Contacto("Juan", "perez", 444, "sdf@gmail.com");
    Duenio lucas = ServiceRegistroUsuarios.getRegistroUsuarios().registrarDuenio("capriLuc", "sososososolokeoek", "Marcos", LocalDate.of(2000, 2, 12), "dni", "424444444", Arrays.asList(contactoLucas), Arrays.asList(scooby));
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertEquals(lucas, RepositorioUsuario.getRepositorioUsuario().encontrarDuenio(scooby));
  }

}
