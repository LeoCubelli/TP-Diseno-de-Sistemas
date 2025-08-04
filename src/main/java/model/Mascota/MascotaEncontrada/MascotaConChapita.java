package model.Mascota.MascotaEncontrada;

import model.Asociacion.Ubicacion;
import model.Entidad.EntidadPersistente;
import model.Mascota.Mascota;
import model.Persona.Rescatista;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "mascota_con_chapita")
public class MascotaConChapita extends EntidadPersistente {
  @Column(columnDefinition = "DATE")
  private LocalDate fecha;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Rescatista rescatista;
  @Embedded
  private Ubicacion ubicacion;
  @Column
  private String descripcion;
  @ElementCollection
  private List<String> fotos;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Mascota mascota;

  public MascotaConChapita(LocalDate fecha, Rescatista rescatista, Ubicacion ubicacion, Mascota mascota, String descripcion, List<String> fotos) {
    this.fecha = fecha;
    this.rescatista = rescatista;
    this.ubicacion = ubicacion;
    this.mascota = mascota;
    this.descripcion = descripcion;
    this.fotos = fotos;
  }

  public MascotaConChapita() {

  }

  public Rescatista getRescatista() {
    return rescatista;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public Mascota getMascota() {
    return mascota;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public List<String> getFotos() {
    return fotos;
  }
}