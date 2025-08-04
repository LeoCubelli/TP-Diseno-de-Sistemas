package model.Hogar;

import model.Asociacion.Ubicacion;
import model.Mascota.MascotaEncontrada.MascotaSinChapita;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class Hogar {
  private String id;
  private String nombre;
  private Ubicacion ubicacion;
  private String telefono;
  private int capacidad;
  private int lugares_disponibles;
  @SerializedName("patio")
  private boolean tienePatio;
  private List<String> caracteristicas;
  private HashMap<String, Boolean> admisiones;

  public boolean aceptaAnimal(String animal) {
    return admisiones.get(animal);
  }

  public boolean tienePatio() {
    return tienePatio;
  }

  public double latitud() {
    return ubicacion.getLatitud();
  }

  public double longitud() {
    return ubicacion.getLongitud();
  }

  public String direccion() {
    return ubicacion.getDireccion();
  }

  public String getId() {
    return id;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public String getTelefono() {
    return telefono;
  }

  public int getCapacidad() {
    return capacidad;
  }

  public int getLugares_disponibles() {
    return lugares_disponibles;
  }

  public List<String> getCaracteristicas() {
    return caracteristicas;
  }

  public HashMap<String, Boolean> getAdmisiones() {
    return admisiones;
  }

  public String getNombre() {
    return nombre;
  }

  public boolean tieneEspacioDisponible() {
    return lugares_disponibles > 0;
  }

  public boolean tieneCaracteristicasDeseadas(MascotaSinChapita mascotaSinChapita){
    return caracteristicas.stream().allMatch(c -> mascotaSinChapita.getDescripcionCaracteristicas().stream().anyMatch(cc -> cc.equals(c)));
  }
  
  public boolean aceptaMascota(MascotaSinChapita mascotaSinChapita) {
    return aceptaAnimal(mascotaSinChapita.getTipoMascota()) && puedeAlbergarMascota(mascotaSinChapita);
  }

  public boolean puedeAlbergarMascota(MascotaSinChapita mascotaSinChapita){
    return (mascotaSinChapita.necesitaPatio() && tienePatio)
        || !(mascotaSinChapita.necesitaPatio())
        && tieneEspacioDisponible() ;
  }

}
