import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioBinario {
    private final File fichero;

    public RepositorioBinario(String ruta) {
        this.fichero = new File(ruta);
    }

    public void inicializar() throws IOException {
        if (!fichero.exists()) {
            System.out.println("recetas.dat no existe: se crea el catálogo inicial.");
            guardarTodas(catalogoInicial());
        }
    }

    private List<Receta> catalogoInicial() {
        List<Receta> l = new ArrayList<>();
        l.add(new Receta(1, "Tortilla de patatas", "Principal", 40, 4, "huevos, patatas, cebolla, aceite"));
        l.add(new Receta(2, "Gazpacho", "Entrante", 15, 4, "tomate, pepino, pimiento, ajo, aceite"));
        l.add(new Receta(3, "Paella valenciana", "Principal", 90, 6, "arroz, pollo, judías, garrofón, azafrán"));
        l.add(new Receta(4, "Crema catalana", "Postre", 30, 4, "leche, yemas, azúcar, canela"));
        return l;
    }

    public void guardarTodas(List<Receta> recetas) throws IOException {
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(fichero)))) {
            for (Receta r : recetas) {
                out.writeInt(r.getId());
                out.writeUTF(r.getNombre());
                out.writeUTF(r.getCategoria());
                out.writeInt(r.getTiempoPreparacion());
                out.writeInt(r.getPorciones());
                out.writeUTF(r.getIngredientes());
            }
        }
    }

    public List<Receta> leerTodas() throws IOException {
        List<Receta> lista = new ArrayList<>();
        if (!fichero.exists()) return lista;
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream(fichero)))) {
            while (true) {
                try {
                    int id = in.readInt();
                    String nombre = in.readUTF();
                    String cat = in.readUTF();
                    int tiempo = in.readInt();
                    int porc = in.readInt();
                    String ingr = in.readUTF();
                    lista.add(new Receta(id, nombre, cat, tiempo, porc, ingr));
                } catch (EOFException fin) {
                    break;
                }
            }
        }
        return lista;
    }

    public Receta buscarPorId(int id) throws IOException {
        for (Receta r : leerTodas()) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    public boolean modificarTiempo(int id, int nuevoTiempo) throws IOException {
        List<Receta> lista = leerTodas();
        for (Receta r : lista) {
            if (r.getId() == id) {
                r.setTiempoPreparacion(nuevoTiempo);
                guardarTodas(lista);
                return true;
            }
        }
        return false;
    }

    public boolean anadir(Receta nueva) throws IOException {
        List<Receta> lista = leerTodas();
        for (Receta r : lista) {
            if (r.getId() == nueva.getId()) return false;
        }
        lista.add(nueva);
        guardarTodas(lista);
        return true;
    }

    public int contar() throws IOException {
        return leerTodas().size();
    }
}