package model.Usuario;

import model.Entidad.EntidadPersistente;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,name = "tipo_usuario")
public class Usuario extends EntidadPersistente {
  @Column
  private String nombreUsuario;
  @Column
  private String contrasenia;

  public Usuario(String nombreUsuario, String contrasenia) {
    if(!RepositorioUsuario.getRepositorioUsuario().existeNombreUsuario(nombreUsuario)){
      if(ValidadorContrasenia.getValidadorContrasenia().esContraseniaValida(contrasenia)){
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
      } else {
        throw new RuntimeException("Contraseña inválida, por favor seleccione otra");
      }
    } else {
      throw new RuntimeException("Ya existe el nombre de usuario indicado!");
    }
  }

  public Usuario() {

  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public String getContrasenia() {
    return contrasenia;
  }

}
