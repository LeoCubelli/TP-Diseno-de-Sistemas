package server;

import model.Negocio.EntityHelper;
import model.Negocio.EnvioRecomendaciones;
import spark.Spark;

public class Server {
  public static void main(String[] args) {
    Spark.port(Integer.parseInt(System.getenv("PORT")));
    //Spark.port(9000);
    EnvioRecomendaciones.getEnvioRecomendaciones().activarEnvioDeRecomendaciones();
    Router.configure();

    EntityHelper.getEntityHelper().iniciarDB();
    EntityHelper.getEntityHelper().inicializador();
  }
}
