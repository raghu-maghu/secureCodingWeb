package xmlattacks;
import java.io.FileInputStream;

import javax.xml.bind.*;
import javax.xml.stream.*;
import javax.xml.transform.stream.StreamSource;


public class JaxbAttack {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
        JAXBContext jc = JAXBContext.newInstance(Customer.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        
        XMLInputFactory xif = XMLInputFactory.newFactory();
       // xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        //xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        java.io.File input = new java.io.File("C:\\Users\\aditya\\workspace\\secureCodingTrainingWeb\\src\\xmlattacks\\input.xml");
        System.out.println("xif === " + xif);
        
        XMLStreamReader xsr = xif.createXMLStreamReader( new StreamSource(new FileInputStream (input)));

        System.out.println("XSR === " + xsr);
        
        Customer customer = (Customer) unmarshaller.unmarshal(xsr);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(customer, System.out);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
    }


}
