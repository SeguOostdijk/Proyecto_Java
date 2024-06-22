package reservas.logica;

public class CursoExtension extends Reservable{
    private String descripcion;
    private int cantidadClases;
    private float costo;

    public CursoExtension(String codigoIdentificador, int cantidadInscriptos, int cantidadClases, String descripcion, float costo) {
        super(codigoIdentificador, cantidadInscriptos);
        this.cantidadClases = cantidadClases;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadClases() {
        return cantidadClases;
    }

    public void setCantidadClases(int cantidadClases) {
        this.cantidadClases = cantidadClases;
    }

    public float getCostoTotal() {
        return costo*getCantidadInscriptos();
    }

    public float getCostoAlumno(){
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Curso de Extension{" +
                "cantidad de clases=" + cantidadClases +
                ", costo=" + costo +
                ", descripcion='" + descripcion + '\'' +
                 super.toString();
    }
}
