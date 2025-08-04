package model.Usuario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class Top10kPeoresContrasenias implements CriterioContrasenia {

  public boolean esContraseniaValida(String contrasenia) {
    File file = new File("10k most common.txt");
    boolean esValida = true;

    try {
      Stream<String> linesStream = Files.lines(file.toPath());
      if(linesStream.anyMatch(l -> l.equals(contrasenia))){
        esValida = false;
      }
      linesStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return esValida;
  }
}
