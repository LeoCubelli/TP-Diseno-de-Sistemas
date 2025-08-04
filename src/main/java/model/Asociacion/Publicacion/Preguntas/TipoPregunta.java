package model.Asociacion.Publicacion.Preguntas;

public enum TipoPregunta {
  GENERAL, //Son obligatorias y son para todas las asociaciones.
  OPCIONAL, //La pregunta se puede contestar o no. Están asociadas a una asociación en particular.
  OBLIGATORIA //La pregunta debe contestarse. Están asociadas a una asociación en particular.
}
