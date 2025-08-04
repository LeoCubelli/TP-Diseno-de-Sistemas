package model.Asociacion;

import model.Asociacion.Publicacion.Preguntas.Pregunta;
import model.Asociacion.Publicacion.PublicacionMascotaDeseada;
import model.Asociacion.Publicacion.PublicacionMascotaParaAdopcion;
import model.Asociacion.Publicacion.PublicacionMascotaSinChapita;
import model.Entidad.EntidadPersistente;
import model.Negocio.EntityHelper;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "asociacion")
public class Asociacion extends EntidadPersistente {
  @Embedded
  private Ubicacion ubicacion; // son coordenadas

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "asociacion_id", referencedColumnName = "id")
  private List<PublicacionMascotaSinChapita> publicacionesSinChapita;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "asociacion_id", referencedColumnName = "id")
  private List<PublicacionMascotaParaAdopcion> publicacionesParaAdopcion;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "asociacion_id", referencedColumnName = "id")
  private List<PublicacionMascotaDeseada> publicacionesMascotaDeseada;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "pregunta_por_asociacion",
      joinColumns = {@JoinColumn(name = "asociacion_id")},
      inverseJoinColumns = {@JoinColumn(name = "pregunta_id")})
  private List<Pregunta> preguntasParaDarEnAdopcion;

  public Asociacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
    this.publicacionesSinChapita = new ArrayList<>();
    this.publicacionesParaAdopcion = new ArrayList<>();
    this.publicacionesMascotaDeseada = new ArrayList<>();
    this.preguntasParaDarEnAdopcion = new ArrayList<>();
  }

  public Asociacion() {

  }

  public void concretarAdopcion(PublicacionMascotaParaAdopcion publicacionMascotaParaAdopcion) {
    publicacionMascotaParaAdopcion.darDeBaja();
  }

  public void agregarPregunta(Pregunta pregunta) {
    preguntasParaDarEnAdopcion.add(pregunta);
    actualizarPreguntas(pregunta);
  }

  private void actualizarPreguntas(Pregunta pregunta) {
    if (!pregunta.esGeneral()) {
      EntityHelper.getEntityHelper().actualizar();
    }
  }

  public void quitarPregunta(Pregunta pregunta) {
    preguntasParaDarEnAdopcion.remove(pregunta);
    actualizarPreguntas(pregunta);
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public void agregarPublicacion(PublicacionMascotaSinChapita publicacionMascotaSinChapita) {
    publicacionesSinChapita.add(publicacionMascotaSinChapita);
    EntityHelper.getEntityHelper().actualizar();
  }

  public void agregarPublicacion(PublicacionMascotaParaAdopcion publicacionMascotaParaAdopcion) {
    publicacionesParaAdopcion.add(publicacionMascotaParaAdopcion);
    EntityHelper.getEntityHelper().actualizar();
  }

  public void agregarPublicacion(PublicacionMascotaDeseada publicacionMascotaDeseada) {
    publicacionesMascotaDeseada.add(publicacionMascotaDeseada);
    EntityHelper.getEntityHelper().actualizar();
  }

  public List<PublicacionMascotaSinChapita> getPublicacionesSinChapita() {
    return publicacionesSinChapita;
  }

  public List<PublicacionMascotaParaAdopcion> getPublicacionesParaAdopcion() {
    return publicacionesParaAdopcion;
  }

  public List<PublicacionMascotaDeseada> getPublicacionesMascotaDeseada() {
    return publicacionesMascotaDeseada;
  }

  // solo para el test

  public boolean contieneMascotaConDescripcion(String descripcion) {
    return publicacionesSinChapita.stream().map(p -> p.getMascota().getDatosMascota().getDescripcion()).collect(Collectors.toList()).contains(descripcion);
  }

  public List<Pregunta> getPreguntasParaDarEnAdopcion() {
    return preguntasParaDarEnAdopcion;
  }
}
