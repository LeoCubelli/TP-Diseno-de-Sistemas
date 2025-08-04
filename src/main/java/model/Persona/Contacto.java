package model.Persona;

import model.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

//Vale la pena persistir esta clase? A priori parecería no tener identidad.
@Entity
@Table(name = "contacto")
public class Contacto extends EntidadPersistente {
  @Column
  private String nombre;
  @Column
  private String apellido;
  @Column
  private Integer telefono;
  @Column
  private String email;

  public Contacto(String nombre, String apellido, Integer telefono, String email) {
    this.nombre = Objects.requireNonNull(nombre,"Falta el nombre de contacto");
    this.apellido = Objects.requireNonNull(apellido,"Falta el apellido de contacto");
    this.telefono = Objects.requireNonNull(telefono,"Falta telefono de contacto");
    this.email = Objects.requireNonNull(email,"Falta email de contacto");
  }

  public Contacto() {

  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public Integer getTelefono() {
    return telefono;
  }

  public String getEmail() {
    return email;
  }
}
