package model.Persona;

import model.Usuario.Usuario;
import model.Asociacion.Publicacion.PublicacionMascotaSinChapita;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("voluntario")
public class Voluntario extends Usuario {

  public Voluntario (String usuario, String contrasenia) {
    super(usuario,contrasenia);
  }
  public Voluntario() {
    super();
  }

  public void aceptarPublicacion(PublicacionMascotaSinChapita publicacion) {
    publicacion.aceptarPublicacion();
  }
}