import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.regex.*;

public class GestorConfiguracion {
    private final Path ruta;

    public GestorConfiguracion(String ruta) {
        this.ruta = Paths.get(ruta);
    }

    public void guardar(Configuracion c) throws IOException {
        String json = "{\n  \"ultimaOpcion\": " + c.getUltimaOpcion()
                + ",\n  \"totalRecetas\": " + c.getTotalRecetas() + "\n}\n";
        Files.write(ruta, json.getBytes(StandardCharsets.UTF_8));
    }

    public Configuracion cargar() throws IOException {
        if (!Files.exists(ruta)) return new Configuracion();
        String json = new String(Files.readAllBytes(ruta), StandardCharsets.UTF_8);
        return new Configuracion(leerEntero(json, "ultimaOpcion"), leerEntero(json, "totalRecetas"));
    }

    private int leerEntero(String json, String clave) throws IOException {
        Matcher m = Pattern.compile("\"" + clave + "\"\\s*:\\s*(-?\\d+)").matcher(json);
        if (!m.find()) throw new IOException("JSON incorrecto: falta la clave " + clave);
        return Integer.parseInt(m.group(1));
    }
}