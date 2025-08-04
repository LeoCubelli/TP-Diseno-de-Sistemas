package model.Servicios.Hogares;

import model.Servicios.Hogares.Entidades.ListadoHogares;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitAPI {
  @GET("hogares")
  Call<ListadoHogares> hogares(@Query("offset") int nroOffset, @Header("Authorization") String authHeader);
}
