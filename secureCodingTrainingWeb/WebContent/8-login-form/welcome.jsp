<%@ page import="com.ldap.*" %>
<%
LdapResult lr = (LdapResult)request.getAttribute("LdapResult");
Person person = (Person)request.getAttribute("User");
 %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Web Template</title>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" marginheight="0" marginwidth="0" bgcolor="#FFFFFF">


<table border="0" width="100%" cellspacing="0" cellpadding="0" background="8-login-form/img/topbkg.gif">
  <tr>
    <td width="50%"><img border="0" src="8-login-form/img/toplogo.gif" width="142" height="66"></td>
    <td width="50%">
      <p align="right"><img border="0" src="8-login-form/img/topright.gif" width="327" height="66"></td>
  </tr>
</table>
<p style="margin-left: 20"><b><font color="#B8C0F0" face="Arial" size="2">&nbsp;</font></b><font face="Arial" size="2" color="#000000">
Welcome <%= person.getName() %> your login was <%= lr.getResultDesc() %>
</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp; </font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<table border="0" width="100%" cellspacing="0" cellpadding="0" background="img/botline.gif">
  <tr>
    <td width="100%"><img border="0" src="img/botline.gif" width="41" height="12"></td>
  </tr>
</table>

</body>

</html>
