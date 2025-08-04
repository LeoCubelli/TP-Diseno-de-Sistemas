package model.Mascota.MascotaEncontrada;

import model.Asociacion.Ubicacion;
import model.Entidad.EntidadPersistente;
import model.Mascota.Caracteristicas.CaracteristicaMascota;
import model.Mascota.DatosMascota;
import model.Mascota.TamanioMascota;
import model.Persona.Rescatista;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "mascota_sin_chapita")
public class MascotaSinChapita extends EntidadPersistente {
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private DatosMascota datosMascota;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Rescatista rescatista;
  @Embedded
  private Ubicacion ubicacion;
  @Column(columnDefinition = "DATE")
  private LocalDate fecha;

  public MascotaSinChapita(DatosMascota datosMascota, Rescatista rescatista, Ubicacion ubicacion, LocalDate fecha) {
    this.datosMascota = datosMascota;
    this.rescatista = rescatista;
    this.ubicacion = ubicacion;
    this.fecha = fecha;
  }

  public MascotaSinChapita() {

  }

  public String getTipoMascota(){
    return datosMascota.getTipoMascota();
  }

  public boolean necesitaPatio(){
    return !datosMascota.getTamanio().equals(TamanioMascota.PEQUEÑA);
  }

  public List<String> getDescripcionCaracteristicas(){
    return getCaracteristicas().stream().map(c -> c.getDescripcion()).collect(Collectors.toList());
  }

  public List<CaracteristicaMascota> getCaracteristicas() {
    return datosMascota.getCaracteristicas();
  }

  public DatosMascota getDatosMascota() {
    return datosMascota;
  }

  public Rescatista getRescatista() {
    return rescatista;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public LocalDate getFecha() {
    return fecha;
  }
}
