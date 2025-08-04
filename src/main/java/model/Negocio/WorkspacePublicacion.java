package model.Negocio;

import model.Asociacion.Asociacion;
import model.Asociacion.Publicacion.*;
import model.Asociacion.Publicacion.Preguntas.Respuesta;
import model.Persona.Contacto;
import model.Servicios.Mails.MailHandler;

import java.util.List;

public class WorkspacePublicacion {

  private static final WorkspacePublicacion publicacionPatitas = new WorkspacePublicacion();

  private WorkspacePublicacion() {

  }

  public static WorkspacePublicacion getPublicacionPatitas() {
    return publicacionPatitas;
  }

  public PublicacionMascotaDeseada publicarMascotaDeseada( List<Respuesta> respuestas, Contacto contacto, Asociacion asociacion) {
    PublicacionMascotaDeseada publicacion = new PublicacionMascotaDeseada(respuestas,contacto);
    asociacion.agregarPublicacion(publicacion);
    MailHandler.getMailHandler().enviarMensaje(contacto.getEmail(),"Link para dar de baja: ..."); //todo tiene que ser el link para acceder al metodo de dar de baja a la publicacion
    return publicacion;
  }

  public PublicacionMascotaParaAdopcion publicarMascotaParaAdopcion(Contacto contactoDuenio, Asociacion asociacion, List<Respuesta> respuestas) {
    PublicacionMascotaParaAdopcion publicacion = new PublicacionMascotaParaAdopcion(contactoDuenio, respuestas);
    asociacion.agregarPublicacion(publicacion);
    return publicacion;
  }

  public void darDeBajaPublicacion(PublicacionMascotaDeseada publicacionMascotaDeseada) {
    publicacionMascotaDeseada.darDeBaja();
  }

//  public List<Pregunta> generarListaPreguntas(model.Asociacion asociacion) {
//    List<Pregunta> preguntas = new ArrayList<>();
//    RepositorioPreguntas.getRepositorioPreguntas().getPreguntas().forEach(p -> preguntas.add(new Pregunta(p.getPregunta(), p.getEsObligatoria())));
//    asociacion.getPreguntasParaDarEnAdopcion().forEach(p -> preguntas.add(new Pregunta(p.getPregunta(), p.getEsObligatoria())));
//    return preguntas;
//  }
//
//  public void responderPreguntas(List<Pregunta> preguntas, List<String> respuestas) {
//    for(int i = 0; i<preguntas.size(); i++) {
//      preguntas.get(i).cargarRespuesta(respuestas.get(i));
//    }
//  }
}