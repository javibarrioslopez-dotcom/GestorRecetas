public class Configuracion {
    private int ultimaOpcion;
    private int totalRecetas;

    public Configuracion() { this(0, 0); }
    public Configuracion(int ultimaOpcion, int totalRecetas) {
        this.ultimaOpcion = ultimaOpcion;
        this.totalRecetas = totalRecetas;
    }

    public int getUltimaOpcion() { return ultimaOpcion; }
    public void setUltimaOpcion(int u) { this.ultimaOpcion = u; }
    public int getTotalRecetas() { return totalRecetas; }
    public void setTotalRecetas(int t) { this.totalRecetas = t; }
}