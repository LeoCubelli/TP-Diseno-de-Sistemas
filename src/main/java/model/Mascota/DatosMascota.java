package model.Mascota;

import model.Entidad.EntidadPersistente;
import model.Mascota.Caracteristicas.CaracteristicaMascota;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "datos_mascota")
public class DatosMascota extends EntidadPersistente {
  @Column
  private String descripcion;
  @ElementCollection
  private List<String> fotos;
  @Column
  private String tipoMascota; //"perro","gato"
  @Column
  private String sexo;
  @Enumerated(EnumType.STRING)
  private TamanioMascota tamanio;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "datos_mascota_id", referencedColumnName = "id")
  private List<CaracteristicaMascota> caracteristicas;

  public DatosMascota(String descripcion, List<String> fotos, String tipoMascota, String sexo, TamanioMascota tamanio, List<CaracteristicaMascota> caracteristicas) {
    this.descripcion = descripcion;
    this.fotos = fotos;
    this.tipoMascota = tipoMascota;
    this.sexo = sexo;
    this.tamanio = tamanio;
    this.caracteristicas = caracteristicas;
  }

  public DatosMascota() {

  }

  public String getDescripcion() {
    return descripcion;
  }

  public List<String> getFotos() {
    return fotos;
  }

  public String getTipoMascota() {
    return tipoMascota;
  }

  public String getSexo() {
    return sexo;
  }

  public TamanioMascota getTamanio() {
    return tamanio;
  }

  public List<CaracteristicaMascota> getCaracteristicas() {
    return caracteristicas;
  }

  public String getPrincipalImage() { return this.fotos.get(0); }
}
