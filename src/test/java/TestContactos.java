import model.Asociacion.Publicacion.RepositorioPreguntas;
import model.Asociacion.RepositorioAsociaciones;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.MascotaEncontrada.RepositorioMascotaEncontrada;
import model.Persona.Contacto;
import model.Usuario.RepositorioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestContactos {

  @BeforeEach
  void init() {
    RepositorioAsociaciones.getRepositorioAsociaciones().clear();
    RepositorioUsuario.getRepositorioUsuario().clear();
    RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().clear();
    RepositorioCaracteristicas.getTipoCaracteristicas().clear();
    RepositorioPreguntas.getRepositorioPreguntas().clear();
  }

  @Test
  public void testFallaAlConstruirContactoConValoresNulos() {
    assertThrows(RuntimeException.class, () -> new Contacto("Juan", "perez", 444, null));
  }

  @Test
  public void testNoFallaAlConstruirContactoConValoresNoNulos() {
    assertDoesNotThrow(() -> new Contacto("Juan", "perez", 444, "sdf@gmail.com"));
  }
}
