import model.Persona.Contacto;
import model.Persona.Rescatista;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRescatistas {
  @Test
  public void testSeCreaRescatistaSatisfactoriamente() {
    Contacto juan = new Contacto("Juan", "perez", 444, "sdf@gmail.com");

    Rescatista rescatista = new Rescatista("Juan", LocalDate.of(2000, 03, 07), "dni", "44444", Arrays.asList(juan));
    assertEquals("Juan", rescatista.getNombre());
  }

  @Test
  public void testFallaAlConstruirRescatistaConValoresNulos() {
    assertThrows(RuntimeException.class, () -> new Rescatista("Juan", LocalDate.of(2000, 03, 07), null, "44444", null));
  }
}
