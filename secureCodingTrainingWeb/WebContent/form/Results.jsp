<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
try{
	java.util.ArrayList al = (java.util.ArrayList)request.getAttribute("TransList");


 %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Results Page</title>
<script type="text/javascript">
	window.onload=function(){
	var tfrow = document.getElementById('tfhover').rows.length;
	var tbRow=[];
	for (var i=1;i<tfrow;i++) {
		tbRow[i]=document.getElementById('tfhover').rows[i];
		tbRow[i].onmouseover = function(){
		  this.style.backgroundColor = '#f3f8aa';
		};
		tbRow[i].onmouseout = function() {
		  this.style.backgroundColor = '#ffffff';
		};
	}
};
</script>

<style type="text/css">
table.tftable {font-size:12px;color:#333333;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
table.tftable th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
table.tftable tr {background-color:#ffffff;}
table.tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}
</style>
</head>
<body>
<!-- Row Highlight Javascript -->

Account Transfers : 
<table id="tfhover" class="tftable" border="1" >
<tr><th>TransId</th><th>TransDate</th><th>UserId</th><th>TransAmount</th><th>CreditAccount</th><th>DebitAccount</th><th>Remarks</th></tr>
<%  
	for(int i=0;i< al.size();i++)
	{
		test.accounttransfer.AccountTransferCom atc = (test.accounttransfer.AccountTransferCom) al.get(i);

%>
<tr>
<td><%= atc.getTransId() %></td>
<td><%= atc.getTransDate() %></td>
<td><%= atc.getUserId() %></td>
<td><%= atc.getTransAmount() %></td>
<td><%= atc.getCreditAccount() %></td>
<td><%= atc.getDebitAccount() %></td>
<td><%= atc.getTransRermarks() %></td>

</tr>
<%

	}
 %>
</table>

<p><small>Created with the <a href="http://www.textfixer.com/html/html-table-generator.php" target="_blank">HTML Table Generator</a></small></p>
</body>
</html>

<%
}catch (Exception ex){
		ex.getStackTrace();
	}
	


 %>
