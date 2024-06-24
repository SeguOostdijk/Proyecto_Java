package reservas.excepciones;
/** Excepcion propia que se lanza cuando el aula que intenta reservar no esta disponible*/
public class AulaOcupadaException extends Exception{
    public AulaOcupadaException(String mensaje){
        super(mensaje);
    }
}
