package model.Servicios.Hogares;

import model.Hogar.Hogar;
import model.Mascota.MascotaEncontrada.MascotaSinChapita;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioHogares {
  private static final RepositorioHogares repositorioHogares = new RepositorioHogares();
  private HogaresAPI hogaresAPI;

  private RepositorioHogares(){
    this.hogaresAPI = new HogaresAPI();
  }

  public List<Hogar> getHogares() {
    List<Hogar> hogares = new ArrayList<>();
    for(int i = 1;i <= hogaresAPI.getCantidadPaginas();i++){
      hogares.addAll(hogaresAPI.obtenerHogares(i));
    }
    return hogares;
  }

  public static RepositorioHogares getRepositorioHogares(){
    return repositorioHogares;
  }

  public List<Hogar> getHogaresApropiados(int radioCercania, MascotaSinChapita mascotaSinChapita) {
    return getHogares()
        .stream()
        .filter(h -> h.getUbicacion().distanciaA(mascotaSinChapita.getUbicacion())<=radioCercania)
        .filter(hogar -> hogar.aceptaMascota(mascotaSinChapita))
        .collect(Collectors.toList());
  }
}
