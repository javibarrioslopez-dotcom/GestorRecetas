public class Receta {
    private int id;
    private String nombre;
    private String categoria;
    private int tiempoPreparacion;
    private int porciones;
    private String ingredientes;

    public Receta(int id, String nombre, String categoria, int tiempoPreparacion,
                  int porciones, String ingredientes) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tiempoPreparacion = tiempoPreparacion;
        this.porciones = porciones;
        this.ingredientes = ingredientes;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getTiempoPreparacion() { return tiempoPreparacion; }
    public int getPorciones() { return porciones; }
    public String getIngredientes() { return ingredientes; }
    public void setTiempoPreparacion(int t) { this.tiempoPreparacion = t; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - %d min - %d porciones%n      Ingredientes: %s",
                id, nombre, categoria, tiempoPreparacion, porciones, ingredientes);
    }
}