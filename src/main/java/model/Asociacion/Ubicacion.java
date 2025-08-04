package model.Asociacion;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;

@Embeddable
public class Ubicacion {
  private String direccion;
  @SerializedName("lat")
  private double latitud;
  @SerializedName("long")
  private double longitud;

  public Ubicacion(String direccion, double latitud, double longitud) {
    this(latitud,longitud);
    this.direccion = direccion;
  }

  public Ubicacion(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public Ubicacion() {

  }

  public String getDireccion() {
    return direccion;
  }

  public double getLatitud() {
    return latitud;
  }

  public double getLongitud() {
    return longitud;
  }

  public double distanciaA(Ubicacion ubicacion) {
    double x1 = ubicacion.getLatitud();
    double y1 = ubicacion.getLongitud();
    double x2 = latitud;
    double y2 = longitud;
    return Math.sqrt(Math.pow(y2 - y1,2) + Math.pow(x2-x1,2));
  }
}
