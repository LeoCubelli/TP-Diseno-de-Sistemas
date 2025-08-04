package model.Asociacion.Publicacion;

import model.Entidad.EntidadPersistente;
import model.Mascota.MascotaEncontrada.MascotaSinChapita;

import javax.persistence.*;

@Entity
@Table(name = "publicacion_mascota_sin_chapita")
public class PublicacionMascotaSinChapita extends EntidadPersistente {
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private MascotaSinChapita mascotaSinChapita;
  @Column
  private boolean esPublica;

  public PublicacionMascotaSinChapita(MascotaSinChapita mascotaSinChapita) {
    this.mascotaSinChapita = mascotaSinChapita;
    this.esPublica = false;
  }

  public boolean esPublica() {
    return esPublica;
  }

  public void aceptarPublicacion() {
    this.esPublica = true;
  }

  public MascotaSinChapita getMascota() {
    return mascotaSinChapita;
  }
}
