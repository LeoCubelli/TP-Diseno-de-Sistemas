package model.Mascota.Caracteristicas;

import model.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristica_mascota")
public class CaracteristicaMascota extends EntidadPersistente {
  @Column
  private String tipo;
  @Column
  private String descripcion;

  public CaracteristicaMascota(String tipo, String descripcion) {
    if(!existeElTipo(tipo)){
      throw new RuntimeException("No existe la característica especificada");
    }else{
      this.tipo = tipo;
      this.descripcion = descripcion;
    }
  }

  public CaracteristicaMascota() {

  }

  private boolean existeElTipo(String tipo) {
    return RepositorioCaracteristicas.getTipoCaracteristicas().existeElTipo(tipo);
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getTipo() {
    return tipo;
  }
}
