import model.Asociacion.Asociacion;
import model.Asociacion.Publicacion.RepositorioPreguntas;
import model.Asociacion.RepositorioAsociaciones;
import model.Asociacion.Ubicacion;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.MascotaEncontrada.RepositorioMascotaEncontrada;
import model.Negocio.EntityHelper;
import model.Negocio.WorkspaceAsociacion;
import model.Usuario.RepositorioUsuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAsociaciones {

  @BeforeEach
  void init() {
    RepositorioAsociaciones.getRepositorioAsociaciones().clear();
    RepositorioUsuario.getRepositorioUsuario().clear();
    RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().clear();
    RepositorioCaracteristicas.getTipoCaracteristicas().clear();
    RepositorioPreguntas.getRepositorioPreguntas().clear();
  }

  @Test
  public void testEncontrarAsociacionMasCercana() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    Asociacion a1 = registrarAsociacion(new Ubicacion ("Calle falsta",2,5));
    Asociacion a2 = registrarAsociacion(new Ubicacion ("Calle falsta",10,5));
    Asociacion a3 = registrarAsociacion(new Ubicacion ("Calle falsta",6,9));
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertEquals(a2, RepositorioAsociaciones.getRepositorioAsociaciones().encontrarAsociacionMasCercana(new Ubicacion(11,6)));
  }

  private Asociacion registrarAsociacion(Ubicacion ubicacion) {
    return WorkspaceAsociacion.getRegistroAsociaciones().registrarAsociacion(ubicacion);
  }
}
