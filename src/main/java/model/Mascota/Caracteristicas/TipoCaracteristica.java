package model.Mascota.Caracteristicas;

import model.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_caracteristica")
public class TipoCaracteristica extends EntidadPersistente {
  @Column
  private String tipoCaracteristica;

  public TipoCaracteristica(String tipoCaracteristica) {
    this.tipoCaracteristica = tipoCaracteristica;
  }

  public TipoCaracteristica() {

  }

  public String getTipoCaracteristica() {
    return tipoCaracteristica;
  }
}
