import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.List;

public class ExportadorXML {

    public void exportar(List<Receta> recetas, String ruta)
            throws ParserConfigurationException, TransformerException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element raiz = doc.createElement("recetas");
        doc.appendChild(raiz);

        for (Receta r : recetas) {
            Element e = doc.createElement("receta");
            e.setAttribute("id", String.valueOf(r.getId()));
            e.appendChild(nodo(doc, "nombre", r.getNombre()));
            e.appendChild(nodo(doc, "categoria", r.getCategoria()));
            e.appendChild(nodo(doc, "tiempoPreparacion", String.valueOf(r.getTiempoPreparacion())));
            e.appendChild(nodo(doc, "porciones", String.valueOf(r.getPorciones())));

            Element ingr = doc.createElement("ingredientes");
            for (String i : r.getIngredientes().split(",")) {
                ingr.appendChild(nodo(doc, "ingrediente", i.trim()));
            }
            e.appendChild(ingr);
            raiz.appendChild(e);
        }

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        t.transform(new DOMSource(doc), new StreamResult(new File(ruta)));
    }

    private Element nodo(Document doc, String nombre, String texto) {
        Element e = doc.createElement(nombre);
        e.setTextContent(texto);
        return e;
    }
}