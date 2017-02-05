package xmlattacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.xml.CustomerAccount;
import com.xml.CustomerAccountType;
import com.xml.Person;

/**
 * Servlet implementation class XmlAttacks
 */
public class XmlAttacks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XmlAttacks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		JAXBContext ctx;
		PrintWriter out = response.getWriter();
		try {
			ctx = JAXBContext.newInstance(com.xml.Person.class);
		
	    Unmarshaller u = ctx.createUnmarshaller();    
	    String inXml = getBody(request);
	    System.out.println("The input xml ==== ["+ inXml + "]");
	    StringReader reader = new StringReader(inXml);
	    com.xml.Person p = (com.xml.Person) u.unmarshal(reader);

	    System.out.println("Person:" + p);
	    CustomerAccount acc = new CustomerAccount();
	    acc.setFirstName(p.getFirstName());
	    acc.setLastName(p.getLastName());
	    acc.setAccountNumber(new String("12345679"));
	    acc.setCurrency(new String("INR"));
	    
	    
	    
	    ctx = JAXBContext.newInstance(com.xml.CustomerAccount.class);
	    Marshaller jaxbMarshaller = ctx.createMarshaller();
	    
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(acc, out);
	    
	    System.out.println(p);
	    
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			out.print(e.toString());
		}
	}

	public static String getBody(HttpServletRequest request) throws IOException {

	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}

}
