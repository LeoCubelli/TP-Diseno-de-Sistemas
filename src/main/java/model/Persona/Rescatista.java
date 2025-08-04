package model.Persona;

import model.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rescatista")
public class Rescatista extends EntidadPersistente {
  @Column
  private String nombre;
  @Column(columnDefinition = "DATE")
  private LocalDate fechaDeNacimiento;
  @Column
  private String tipoDocumento;
  @Column
  private String documento;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "rescatista_id", referencedColumnName = "id")
  private List<Contacto> contactos;

  public Rescatista(String nombre, LocalDate fechaDeNacimiento, String tipoDocumento, String documento, List<Contacto> contactos) {
    this.nombre = Objects.requireNonNull(nombre,"Falta nombre");
    this.fechaDeNacimiento = Objects.requireNonNull(fechaDeNacimiento,"Falta fecha de nacimiento");
    this.tipoDocumento = Objects.requireNonNull(tipoDocumento,"Falta tipo de Documento");
    this.documento = Objects.requireNonNull(documento,"Falta numero de documento");
    //Validar que pase al menos un contacto
    this.contactos = Objects.requireNonNull(contactos,"Falta asociar al menos un contacto");
  }

  /*
  public void registrarMascotaEncontrada(LocalDate fechaEncontrada, String descripcion, Ubicacion ubicacion, List<String> fotos, model.Mascota mascota){
    new MascotaEncontradaBuilder().setFecha(fechaEncontrada)
        .setDescripcion(descripcion)
        .setUbicacion(ubicacion)
        .setFotos(fotos).setRescatista(this)
        .setMascota(mascota)
        .generarMascota();
  }

   */

  public String getNombre() {
    return nombre;
  }

  public LocalDate getFechaDeNacimiento() {
    return fechaDeNacimiento;
  }

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public String getDocumento() {
    return documento;
  }

  public List<Contacto> getContactos() {
    return contactos;
  }
}