import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GestorRecetas {
    private static final String FICHERO_DAT = "recetas.dat";
    private static final String FICHERO_XML = "recetas.xml";
    private static final String FICHERO_JSON = "configuracion.json";

    private final RepositorioBinario repo = new RepositorioBinario(FICHERO_DAT);
    private final ExportadorXML exportador = new ExportadorXML();
    private final GestorConfiguracion gestorConfig = new GestorConfiguracion(FICHERO_JSON);
    private final Scanner sc = new Scanner(System.in);
    private Configuracion config = new Configuracion();

    public static void main(String[] args) {
        new GestorRecetas().ejecutar();
    }

    private void ejecutar() {
        try {
            config = gestorConfig.cargar();
            System.out.println("Configuración anterior -> última opción: " + config.getUltimaOpcion()
                    + ", recetas: " + config.getTotalRecetas());
        } catch (IOException e) {
            System.err.println("No se pudo leer la configuración: " + e.getMessage());
        }

        try {
            repo.inicializar();
        } catch (IOException e) {
            System.err.println("Error fatal al preparar el catálogo: " + e.getMessage());
            return;
        }

        int opcion;
        do {
            opcion = menu();
            if (opcion >= 1 && opcion <= 5) config.setUltimaOpcion(opcion);
            procesar(opcion);
        } while (opcion != 0);

        guardarConfiguracion();
        System.out.println("¡Hasta pronto!");
    }

    private int menu() {
        System.out.println("\nGESTOR DE RECETAS");
        System.out.println("1. Mostrar todas las recetas");
        System.out.println("2. Buscar receta por id");
        System.out.println("3. Modificar tiempo de preparación");
        System.out.println("4. Añadir receta");
        System.out.println("5. Exportar a XML");
        System.out.println("0. Salir");
        return leerEntero("Opción: ");
    }

    private void procesar(int opcion) {
        try {
            switch (opcion) {
                case 1 -> mostrarTodas();
                case 2 -> buscar();
                case 3 -> modificarTiempo();
                case 4 -> anadir();
                case 5 -> exportar();
                case 0 -> { }
                default -> System.out.println("Opción no válida.");
            }
        } catch (IOException e) {
            System.err.println("Error de acceso al fichero: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private void mostrarTodas() throws IOException {
        List<Receta> lista = repo.leerTodas();
        if (lista.isEmpty()) System.out.println("El catálogo está vacío.");
        lista.forEach(System.out::println);
    }

    private void buscar() throws IOException {
        int id = leerEntero("Id a buscar: ");
        Receta r = repo.buscarPorId(id);
        System.out.println(r != null ? r : "No existe ninguna receta con id " + id);
    }

    private void modificarTiempo() throws IOException {
        int id = leerEntero("Id de la receta: ");
        int t = leerEntero("Nuevo tiempo (min): ");
        System.out.println(repo.modificarTiempo(id, t) ? "Tiempo actualizado." : "No existe esa receta.");
    }

    private void anadir() throws IOException {
        int id = leerEntero("Id: ");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Categoría: ");
        String cat = sc.nextLine();
        int tiempo = leerEntero("Tiempo (min): ");
        int porc = leerEntero("Porciones: ");
        System.out.print("Ingredientes (separados por coma): ");
        String ingr = sc.nextLine();
        boolean ok = repo.anadir(new Receta(id, nombre, cat, tiempo, porc, ingr));
        System.out.println(ok ? "Receta añadida." : "Ya existe una receta con ese id.");
    }

    private void exportar() throws Exception {
        exportador.exportar(repo.leerTodas(), FICHERO_XML);
        System.out.println("Catálogo exportado a " + FICHERO_XML);
    }

    private void guardarConfiguracion() {
        try {
            config.setTotalRecetas(repo.contar());
            gestorConfig.guardar(config);
        } catch (IOException e) {
            System.err.println("No se pudo guardar la configuración: " + e.getMessage());
        }
    }

    private int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero.");
            }
        }
    }
}