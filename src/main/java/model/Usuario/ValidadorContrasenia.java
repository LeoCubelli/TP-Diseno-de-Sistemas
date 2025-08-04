package model.Usuario;

import java.util.Arrays;
import java.util.List;

public class ValidadorContrasenia {
  private static final ValidadorContrasenia validadorContrasenia = new ValidadorContrasenia();
  private List<CriterioContrasenia> validadores;

  public static ValidadorContrasenia getValidadorContrasenia() {
    return validadorContrasenia;
  }

  public List<CriterioContrasenia> validadores(){
    return validadores;
  }

  public boolean esContraseniaValida(String contrasenia) {
    return validadores.stream().allMatch(validador -> validador.esContraseniaValida(contrasenia));
  }

  //Por default, ya tiene uno (o varios) validadores cargados.
  private ValidadorContrasenia() {
    this.validadores = Arrays.asList(new Top10kPeoresContrasenias());
  }
}
