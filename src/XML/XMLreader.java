package XML;
/**
 *
 * @author oracle
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author oracle
 */
public class XMLreader {

    private XMLStreamReader xstrm = null;
    private final ArrayList<Products> listap = new ArrayList<>();
    private final String[] lecturaXML = new String[9];
    String ruta= "src/XML/Products.xml";
    
    public void leerXML() {
        try {
            xstrm = XMLInputFactory.newInstance()
                    .createXMLStreamReader(new FileInputStream(ruta));
            int x = 0;
            while (xstrm.hasNext()) {
                xstrm.next();
                switch (xstrm.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (xstrm.getAttributeCount() == 1) {
                            lecturaXML[x] = xstrm.getAttributeValue(0);
                            x++;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        lecturaXML[x] = xstrm.getText();
                        x++;
                        break;
                }
            }
            x = 0;
            while (x < lecturaXML.length) {
                listap.add(new Products(lecturaXML[x], lecturaXML[x + 1], Integer.parseInt(lecturaXML[x + 2])));
                x += 3;
            }
            xstrm.close();

        } catch (FactoryConfigurationError e) {
            System.out.println("Error de configuracion");
        } catch(XMLStreamException ex){
            System.out.println("Error de flujo");
        }catch (FileNotFoundException ex){
            System.out.println("Fichero no encontrado");
        }
    }

    public void imprimirArrayList() {

        leerXML();
        int i = 0;
        while (i < listap.size()) {
            System.out.println(listap.get(i).getCodigo() + " " 
           + listap.get(i).getDescripcion() + " " + listap.get(i).getPrezo());
            i++;
        }
    }

    public static void main(String[] args) {
       XMLreader xmlr = new XMLreader();
       xmlr.imprimirArrayList();
        
    }

}