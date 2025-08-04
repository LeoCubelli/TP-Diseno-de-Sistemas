package model.Servicios.Hogares;

import model.Hogar.Hogar;
import model.Servicios.Hogares.Entidades.ListadoHogares;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class HogaresAPI {
  private String token = "bYnqHPGADjonwmPfAxcQtCdDSRUAYDSA1q24pRWD7G26F4acFVwhPNlbY0Vw";
  private String urlApi = "https://api.refugiosdds.com.ar/api/";
  private Retrofit retrofit;

  public HogaresAPI(){
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public List<Hogar> obtenerHogares(int offset) {
    return listadoDeHogares(offset).getHogares();
  }

  public int getCantidadPaginas() {
    ListadoHogares hogares = listadoDeHogares(1);
    int totalHogares = hogares.getTotal();
    int totalHogaresPorPagina = hogares.getHogares().size();
    return totalHogares/totalHogaresPorPagina;
  }

  private ListadoHogares listadoDeHogares(int offset) {
    RetrofitAPI retrofitAPI = this.retrofit.create(RetrofitAPI.class);
    Call<ListadoHogares> requestHogares = retrofitAPI.hogares(offset,"Bearer "+token);
    Response<ListadoHogares> responseHogares = null;
    try {
      responseHogares = requestHogares.execute();
    } catch (IOException e) {
      throw new RuntimeException("No se pudo obtener el listado de hogares");
    }
    return responseHogares.body();
  }
}
