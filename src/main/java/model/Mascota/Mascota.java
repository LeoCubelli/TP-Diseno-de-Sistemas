package model.Mascota;

import model.Entidad.EntidadPersistente;
import model.Mascota.Caracteristicas.CaracteristicaMascota;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mascota")
public class Mascota extends EntidadPersistente {
  @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private DatosMascota datosMascota;
  @Column
  private String nombre;
  @Column
  private String apodo;
  @Column
  private Integer edad;

  public Mascota(String nombre, TamanioMascota tamanio, String apodo, Integer edad, String sexo, String descripcion, List<String> fotos, String tipoMascota, List<CaracteristicaMascota> caracteristicas) {
    this.nombre = nombre;
    this.apodo = apodo;
    this.edad = edad;
    this.datosMascota = new DatosMascota(descripcion,fotos,tipoMascota,sexo,tamanio,caracteristicas);
  }

  public Mascota() {

  }

  public DatosMascota getDatosMascota() {
    return datosMascota;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApodo() {
    return apodo;
  }

  public Integer getEdad() {
    return edad;
  }
}
