<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> <!-- framehtm -->
  <head>
    <title>Frame Set</title>
  </head>
  <frameset rows="20%,*">
 
    <frame name="title" src="title.htm" scrolling="no" noresize>
 
      <frameset cols="30%,*">
 
        <frame name="menu" src="menu.html" scrolling="auto" noresize>
 
        <frame name="content" src="Content.html" scrolling="yes" noresize>
 
      </frameset>
  </frameset>
</html>
