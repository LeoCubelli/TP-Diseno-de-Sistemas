import model.Hogar.Hogar;
import model.Servicios.Hogares.HogaresAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestRetroFit {
  HogaresAPI retrofit;
  Hogar hogar;

  @BeforeEach
  void initRetroFit(){
    retrofit = mock(HogaresAPI.class);
    hogar = mock(Hogar.class);
  }

  @Test
  public void testObtencionHogares() {
    when(retrofit.obtenerHogares(1)).thenReturn(Arrays.asList(hogar));
    when(hogar.getNombre()).thenReturn("Patitas");
    List<Hogar> hogares = retrofit.obtenerHogares(1);
    Hogar casita = hogares.get(0);
    assertEquals(casita.getNombre(),"Patitas");
  }
}
