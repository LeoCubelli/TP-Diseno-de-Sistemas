import model.Asociacion.Publicacion.RepositorioPreguntas;
import model.Asociacion.RepositorioAsociaciones;
import model.Mascota.Caracteristicas.CaracteristicaMascota;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.MascotaEncontrada.RepositorioMascotaEncontrada;
import model.Negocio.EntityHelper;
import model.Persona.Administrador;
import model.Usuario.RepositorioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestCaracteristicas {

  @BeforeEach
  void init() {
    RepositorioAsociaciones.getRepositorioAsociaciones().clear();
    RepositorioUsuario.getRepositorioUsuario().clear();
    RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().clear();
    RepositorioCaracteristicas.getTipoCaracteristicas().clear();
    RepositorioPreguntas.getRepositorioPreguntas().clear();
  }

  @Test
  public void testUsuarioAdminPuedeAgregarCaractsAlSingletonTipoCaracteristicas() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    Administrador administrador = new Administrador("Jorge", "Lllllf.,*");
    administrador.agregarCaracteristicas("colorPelo");
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertTrue(RepositorioCaracteristicas.getTipoCaracteristicas().existeElTipo("colorPelo"));
  }

  @Test
  public void testAgregarCaracteristicaMascotaInValida() {
    assertThrows(RuntimeException.class, () -> new CaracteristicaMascota("aceituna", "negra"));
  }

  @Test
  public void testAgregarCaracteristicaMascotaValida() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    RepositorioCaracteristicas.getTipoCaracteristicas().agregarTipoCaracteristicas("colorPelo");
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertDoesNotThrow(() -> new CaracteristicaMascota("colorPelo", "Marrón"));
  }
}
