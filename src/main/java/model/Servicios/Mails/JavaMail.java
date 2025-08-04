package model.Servicios.Mails;

public class JavaMail implements ServicioMail {
  public void enviarMensaje(String correo,String mensaje){
    System.out.println("Mensaje enviado correctamente a "+correo);
  }
}
