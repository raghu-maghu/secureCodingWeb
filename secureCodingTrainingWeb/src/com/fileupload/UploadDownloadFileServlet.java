package com.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadDownloadFileServlet
 */
public class UploadDownloadFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;
    @Override
    public void init() throws ServletException{
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        if(fileName == null || fileName.equals("")){
            throw new ServletException("File Name can't be null or empty");
        }
        File file = new File(getServletContext().getAttribute("FILES_DIR")+File.separator+fileName);
        if(!file.exists()){
            throw new ServletException("File doesn't exists on server.");
        }
        System.out.println("File location on server::"+file.getAbsolutePath());
        ServletContext ctx = getServletContext();
        InputStream fis = new FileInputStream(file);
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null? mimeType:"application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
         
        ServletOutputStream os       = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read=0;
        while((read = fis.read(bufferData))!= -1){
            os.write(bufferData, 0, read);
        }
        os.flush();
        os.close();
        fis.close();
        System.out.println("File downloaded at client successfully");
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!ServletFileUpload.isMultipartContent(request)){
            throw new ServletException("Content type is not multipart/form-data");
        }
         
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write("<html><head></head><body>");
        try {
        	uploader.setFileSizeMax(1000000);
            List<FileItem> fileItemsList = uploader.parseRequest(request);
            Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
            String customerCode = null;
            while(fileItemsIterator.hasNext()){
                FileItem fileItem = fileItemsIterator.next();
                if(fileItem.isFormField()){
                System.out.println("FieldName="+fileItem.getFieldName());
                customerCode = fileItem.getString();
                System.out.println("CustomerCode="+fileItem.getFieldName());
                
                continue;
                }
                System.out.println("FileName="+fileItem.getName());
                System.out.println("ContentType="+fileItem.getContentType());
                System.out.println("Size in bytes="+fileItem.getSize());
                
                File file = new File("C:\\Temp"+File.separator+fileItem.getName());
                System.out.println("Absolute Path at server="+file.getAbsolutePath());
                fileItem.write(file);
            	Connection con = null;    
        		PreparedStatement stmt = null;
        		ResultSet rs = null;
        		try {
        			Class.forName("com.mysql.jdbc.Driver");
        			con =DriverManager.getConnection ("jdbc:mysql://localhost:3306/test", "root", "root");
        			stmt = con.prepareStatement("INSERT INTO test.FILE_UPLOAD (`FILE_NAME`,`CREATION_DATE`,`STATUS`,`CUSTOMER_CODE`)VALUES(?,curtime(),?,'raghu')");
        			stmt.setString(1, fileItem.getName());
        			stmt.setString(2, "Pending");
        			stmt.execute();
        		}catch (SQLException e) {
        			throw new ServletException("Servlet Could not display records.", e);
      		  } catch (ClassNotFoundException e) {
      			  throw new ServletException("JDBC Driver not found.", e);
      			} finally {
      				try {
      					if(rs != null) {
      						rs.close();
      						rs = null;
      					}
      					if(stmt != null) {
      						stmt.close();
      						stmt = null;
      					}
      					if(con != null) {
      						con.close();
      						con = null;
      					}
      				} catch (SQLException e) {}
      			}
                out.write("File "+fileItem.getName()+ " uploaded successfully.");
                out.write("<br>");
                out.write("<a href=\"UploadDownloadFileServlet?fileName="+fileItem.getName()+"\">Download "+fileItem.getName()+"</a>");
            }
        } catch (FileUploadException e) {
        	e.printStackTrace();
            out.write("Exception in uploading file.");
        } catch (Exception e) {
        	e.printStackTrace();
        	out.write("Exception in uploading file.");
        }
        out.write("</body></html>");
    }
 
}