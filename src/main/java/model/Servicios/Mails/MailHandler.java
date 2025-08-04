package model.Servicios.Mails;

import model.Persona.Contacto;

import java.util.List;

public class MailHandler {
  private static final MailHandler mailHandler = new MailHandler();
  private ServicioMail servicio;

  private MailHandler(){
    this.servicio = new JavaMail();
  }

  public void enviarMensaje(List<Contacto> contactos,String mensaje){
    for(Contacto contacto: contactos)
      if(!contacto.getEmail().isEmpty())
        MailHandler.getMailHandler().enviarMensaje(contacto.getEmail(),mensaje);
  }

  public static MailHandler getMailHandler(){
    return mailHandler;
  }

  public void enviarMensaje(String correo,String mensaje){
    servicio.enviarMensaje(correo,mensaje);
  }
}
