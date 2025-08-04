package model.Persona;

import model.Mascota.Mascota;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import model.Negocio.EntityHelper;
import model.Usuario.*;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.*;

@Entity
@DiscriminatorValue("duenio")
public class Duenio extends Usuario {
  @Column
  private String nombre;
  @Column(columnDefinition = "DATE")
  private LocalDate fechaDeNacimiento;
  @Column
  private String  tipoDocumento;
  @Column
  private String documento;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "duenio_id", referencedColumnName = "id")
  private List<Contacto> contactos;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "duenio_id", referencedColumnName = "id")
  private List<Mascota> mascotas;

  public Duenio(String username, String contrasenia, String nombre, LocalDate fechaDeNacimiento, String tipoDocumento, String documento, List<Contacto> contactos, List<Mascota> mascotas) {
    super(username, contrasenia);
    this.contactos = contactos;
    this.mascotas = mascotas;
    this.nombre = Objects.requireNonNull(nombre, "Por favor, ingrese un nombre.");
    this.fechaDeNacimiento = Objects.requireNonNull(fechaDeNacimiento, "Por favor, ingrese una fecha válida.");
    this.tipoDocumento = Objects.requireNonNull(tipoDocumento, "Por favor, ingrese un documento válido");
    this.documento = Objects.requireNonNull(documento, "Por favor, ingrese un documento");
  }

  public  Duenio() {
    super();
  }

  public void agregarMascota(Mascota mascota) {
    mascotas.add(mascota);
    EntityHelper.getEntityHelper().actualizar();
  }

  public String getNombre() {
    return nombre;
  }

  public void recibirMensaje(String mensaje){
    //Enviar sms o mail.
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

  public List<Mascota> getMascotas() {
    return mascotas;
  }
}
