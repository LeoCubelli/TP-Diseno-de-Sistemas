import model.Asociacion.Asociacion;
import model.Asociacion.Publicacion.RepositorioPreguntas;
import model.Asociacion.RepositorioAsociaciones;
import model.Asociacion.Ubicacion;
import model.Mascota.*;
import model.Mascota.Caracteristicas.RepositorioCaracteristicas;
import model.Mascota.MascotaEncontrada.RepositorioMascotaEncontrada;
import model.Negocio.EntityHelper;
import model.Negocio.ServiceRegistroUsuarios;
import model.Negocio.ServiceRescatista;
import model.Persona.Contacto;
import model.Persona.Duenio;
import model.Persona.Rescatista;
import model.Usuario.RepositorioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestMascotas {

  @BeforeEach
  void init() {
    RepositorioAsociaciones.getRepositorioAsociaciones().clear();
    RepositorioUsuario.getRepositorioUsuario().clear();
    RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().clear();
    RepositorioCaracteristicas.getTipoCaracteristicas().clear();
    RepositorioPreguntas.getRepositorioPreguntas().clear();
  }

  @Test
  public void testCreacionMascotaValida(){
    EntityHelper.getEntityHelper().empezarTransaccion();
    Mascota scooby = generarMascota("Scooby");
    Contacto juan = generarContacto("juan");
    Duenio lucas = generarDuenio("lucas", juan, scooby);
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertEquals("Scooby", lucas.getMascotas().get(0).getNombre());
  }

  @Test
  public void testRegistroMascotaConChapitaEncontrada(){
    EntityHelper.getEntityHelper().empezarTransaccion();
    Contacto contactoTobias = generarContacto("tobias");
    Rescatista tobias = generarRescatista(contactoTobias);

    Mascota scooby = generarMascota("Scooby");
    Contacto contactoJuan = generarContacto("juan");
    Duenio jose = generarDuenio("jose", contactoJuan, scooby); // no se usa pero se agrega al repo de duenios

    ServiceRescatista.getRescatePatitas().registrarMascotaConChapita(tobias, LocalDate.now(), new Ubicacion("asd",2,3),scooby,"Sano", Arrays.asList("link"));
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertEquals("Scooby", RepositorioMascotaEncontrada.getRepositorioMascotaEncontrada().ultimasMascotasEncontradas().get(0).getMascota().getNombre());
  }

  @Test
  public void testRegistroMascotaSinChapitaEncontrada(){
    EntityHelper.getEntityHelper().empezarTransaccion();
    Contacto contactoTobias = generarContacto("tobias");
    Rescatista tobias = generarRescatista(contactoTobias);
    DatosMascota datos = new DatosMascota("Perro Cochino", Arrays.asList("linkFoto"), "perro", "masculino", TamanioMascota.GRANDE, Arrays.asList());

    Asociacion asociacion = new Asociacion (new Ubicacion ("Calle falsta",2,5));
    Asociacion asociacion2 = new Asociacion (new Ubicacion ("Calle falsta",10,5));
    Asociacion asociacion3 = new Asociacion (new Ubicacion ("Calle falsta",6,9));
    RepositorioAsociaciones.getRepositorioAsociaciones().agregarAsociacion(asociacion, asociacion2, asociacion3);
    EntityHelper.getEntityHelper().finalizarTransaccion();

    EntityHelper.getEntityHelper().empezarTransaccion();
    ServiceRescatista.getRescatePatitas().registrarMascotaSinChapita(tobias, datos, LocalDate.now(), new Ubicacion("No sé",2,3));
    Asociacion unaAsociacion = RepositorioAsociaciones.getRepositorioAsociaciones().encontrarAsociacionMasCercana(new Ubicacion("No sé",2,3));
    EntityHelper.getEntityHelper().finalizarTransaccion();
    assertTrue(unaAsociacion.contieneMascotaConDescripcion("Perro Cochino"));
  }

  private Mascota generarMascota(String nombre) {
    return new Mascota(nombre,TamanioMascota.GRANDE,"DOo",5,"Masculino","Perro investigador",Arrays.asList("Una"),"perro",Arrays.asList());
  }

  private Contacto generarContacto(String nombre) {
    return new Contacto(nombre,"perez",444,"sdf@gmail.com");
  }

  private Duenio generarDuenio(String nombre, Contacto contacto, Mascota mascota) {
    return ServiceRegistroUsuarios.getRegistroUsuarios().registrarDuenio(nombre,"sososososolokeoek","Marcos",LocalDate.of(2000,02,12),"DNI","42444444",Arrays.asList(contacto),Arrays.asList(mascota));
  }

  private Rescatista generarRescatista(Contacto contacto) {
    return new Rescatista("Juan",LocalDate.of(2000,03,07), "dni","44444",Arrays.asList(contacto));
  }
}