package model.Servicios.Hogares.Entidades;

import model.Hogar.Hogar;

import java.util.ArrayList;
import java.util.List;

public class ListadoHogares {
  private int total;
  private int offset;
  private List<Hogar> hogares;

  public ListadoHogares(int total,int offset){
    this.hogares = new ArrayList<>();
    this.total = total;
    this.offset = offset;
  }

  public int getTotal() {
    return total;
  }

  public int getOffset() {
    return offset;
  }

  public List<Hogar> getHogares(){
    return hogares;
  }
}
