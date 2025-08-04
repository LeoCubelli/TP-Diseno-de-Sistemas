import model.Hogar.Hogar;
import model.Servicios.Hogares.HogaresAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TestHogares {
  HogaresAPI retrofit;
  Hogar hogar;

  @BeforeEach
  void initRetroFit(){
    retrofit = mock(HogaresAPI.class);
    hogar = mock(Hogar.class);
  }

  @Test
  public void testAdmisionAnimales() {
    when(retrofit.obtenerHogares(3)).thenReturn(Arrays.asList(hogar));
    when(hogar.aceptaAnimal("perros")).thenReturn(true);
    List<Hogar> hogares = retrofit.obtenerHogares(3);
    Hogar casita = hogares.get(0);
    assertTrue(casita.aceptaAnimal("perros"));
  }
}
