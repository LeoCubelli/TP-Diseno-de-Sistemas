import model.Asociacion.Asociacion;
import model.Asociacion.Publicacion.Preguntas.Pregunta;
import model.Asociacion.Publicacion.Preguntas.Respuesta;
import model.Asociacion.Publicacion.Preguntas.TipoPregunta;
import model.Asociacion.Publicacion.RepositorioPreguntas;
import model.Asociacion.RepositorioAsociaciones;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.MascotaEncontrada.RepositorioMascotaEncontrada;
import model.Negocio.EntityHelper;
import model.Negocio.EnvioRecomendaciones;
import model.Negocio.WorkspacePublicacion;
import model.Negocio.WorkspaceAsociacion;
import model.Persona.Contacto;
import model.Usuario.RepositorioUsuario;
import model.Asociacion.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestPublicaciones {

  Pregunta preguntaPatio;
  Pregunta preguntaAdiestramiento;
  Pregunta otraPreguntaPatio;
  Pregunta otraPreguntaAdiestramiento;
  Respuesta respuestaAdiestramiento;
  Respuesta respuestaPatio;
  Respuesta otraRespuestaPatio;
  Respuesta otraRespuestaAdiestramiento;
  Contacto contactoInterasado;
  Contacto contactoDuenio;
  Asociacion asociacion;

  @BeforeEach
  void init() {
    RepositorioAsociaciones.getRepositorioAsociaciones().clear();
    RepositorioUsuario.getRepositorioUsuario().clear();
    RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().clear();
    RepositorioCaracteristicas.getTipoCaracteristicas().clear();
    RepositorioPreguntas.getRepositorioPreguntas().clear();

    preguntaPatio = new Pregunta("¿Necesita patio?", TipoPregunta.OBLIGATORIA);
    preguntaAdiestramiento = new Pregunta("¿Está adiestrado?",TipoPregunta.OPCIONAL);

    respuestaAdiestramiento = new Respuesta(preguntaAdiestramiento,"No");
    respuestaPatio = new Respuesta(preguntaPatio,"No");
    otraRespuestaPatio = new Respuesta(preguntaPatio,"Si");
    otraRespuestaAdiestramiento = new Respuesta(preguntaAdiestramiento,"Si");

    contactoInterasado = new Contacto("Juan", "perez", 444, "sdf@gmail.com");
    contactoDuenio = new Contacto("Roberto","Gomez",12345678,"asd@gmail.com");

    EntityHelper.getEntityHelper().empezarTransaccion();
    asociacion = WorkspaceAsociacion.getRegistroAsociaciones().registrarAsociacion(new Ubicacion("Calle falsta", 2, 5));
    EntityHelper.getEntityHelper().finalizarTransaccion();
  }

  @Test
  public void testRecomendacionDeMascotaEnAdopcionApropiada() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    WorkspacePublicacion.getPublicacionPatitas().publicarMascotaDeseada(Arrays.asList(respuestaAdiestramiento, respuestaPatio), contactoInterasado, asociacion);
    WorkspacePublicacion.getPublicacionPatitas().publicarMascotaParaAdopcion(contactoDuenio,asociacion, Arrays.asList(respuestaAdiestramiento, respuestaPatio));
    WorkspacePublicacion.getPublicacionPatitas().publicarMascotaParaAdopcion(contactoDuenio,asociacion, Arrays.asList(otraRespuestaPatio));
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertEquals(1, EnvioRecomendaciones.getEnvioRecomendaciones().mascotasPosibles(asociacion.getPublicacionesMascotaDeseada().get(0), asociacion.getPublicacionesParaAdopcion()).size());
  }

  @Test
  public void testNoRecomeniendaMascotaEnAdopcionNoApropiada() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    WorkspacePublicacion.getPublicacionPatitas().publicarMascotaDeseada(Arrays.asList(respuestaAdiestramiento, respuestaPatio), contactoInterasado, asociacion);
    WorkspacePublicacion.getPublicacionPatitas().publicarMascotaParaAdopcion(contactoDuenio,asociacion, Arrays.asList(otraRespuestaAdiestramiento, respuestaAdiestramiento, otraRespuestaPatio));
    WorkspacePublicacion.getPublicacionPatitas().publicarMascotaParaAdopcion(contactoDuenio,asociacion, Arrays.asList(otraRespuestaPatio));
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertEquals(0, EnvioRecomendaciones.getEnvioRecomendaciones().mascotasPosibles(asociacion.getPublicacionesMascotaDeseada().get(0), asociacion.getPublicacionesParaAdopcion()).size());
  }

  @Test
  public void testDarDeBajaPublicacion() {
    EntityHelper.getEntityHelper().empezarTransaccion();
    WorkspacePublicacion.getPublicacionPatitas().publicarMascotaDeseada(Arrays.asList(respuestaAdiestramiento, respuestaPatio), contactoInterasado, asociacion);
    WorkspacePublicacion.getPublicacionPatitas().darDeBajaPublicacion(asociacion.getPublicacionesMascotaDeseada().get(0));
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertFalse(asociacion.getPublicacionesMascotaDeseada().get(0).getEsPublica());
  }
}