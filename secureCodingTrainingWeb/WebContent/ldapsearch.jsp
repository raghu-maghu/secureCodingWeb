
<%@ page import="java.util.*" %>
 <%@ page import="javax.naming.*" %>
 <%@ page import="java.util.regex.*" %>
 <%@ page import="javax.naming.directory.*" %>


<% 
// Setup our LDAP options
 Hashtable env = new Hashtable();
 env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
 env.put(Context.PROVIDER_URL, "ldap://localhost:389"); // LDAP host and base

// LDAP authentication options
 //env.put(Context.SECURITY_AUTHENTICATION, "simple");
 //env.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
 //env.put(Context.SECURITY_CREDENTIALS, "password");

DirContext ctx = new InitialDirContext(env);
boolean is_clean = true;

// Get our request parameters POST'd from our HTML form
 String lookup_id = request.getParameter("lookup_id"); // user name or spriden id
 String lookup = request.getParameter("lookup"); // indicates attribute to match on

// Regex to make sure our lookup ID is alphanumeric
/* Pattern clean_pattern = Pattern.compile("^[a-zA-Z0-9]*$");
 Matcher m = clean_pattern.matcher(lookup_id);
 is_clean=m.matches();
*/
// The ID passed from the form contains non alphanumeric characters;
 // print an error message and stop execution
 if(!is_clean) {
 out.print("Please enter a valid ID without any special characters");
 return;
 }

// Now we build out lookup filter
 String filter = null;

if(lookup.equals("loginid")) { // what type of filter should we set?
 filter = "(uid=" + lookup_id + ")";
 }
 else {
 filter = "(pdsExternalSystemId=" + lookup_id + "::sct)";
 }

// Search using the filter we defined
 NamingEnumeration answer = ctx.search("ou=people,dc=example,dc=com", filter, null);

// No matches - empty result set
 if(!answer.hasMore()) {
 	out.print("No record with that ID was found!");
 }

while (answer.hasMore ()) {
 SearchResult result = (SearchResult) answer.next ();
 Attributes attrs = result.getAttributes();
 out.print("Found record :<br>");
 out.print("" + attrs.get("uid") + "<br>"); // immutable ID
 out.print("" + attrs.get("cn") + "<br>"); // full name
 /*out.print("" + attrs.get("pdsLoginId") + ""); // login ID
 out.print("" + attrs.get("pdsExternalSystemID") + ""); // external system IDs
 out.print("" + attrs.get("mail") + ""); // e-mail
 out.print("" + attrs.get("pdsRole") + ""); // roles
 out.print("" + attrs.get("pdsCredentialExpired") + ""); // boolean - are credentials expired?
 out.print("" + attrs.get("pdsAccountStatus") + ""); // account status - usually enabled or suspended
 out.print("" + attrs.get("pdsLoginSuccess") + ""); // most recent successful login (Zulu)
 out.print("" + attrs.get("pdsLoginFailure") + ""); // failed login attempts (Zulu)*/
 out.print("------------------------------------------------------------------------------<br>");
 }
 answer.close();

%> 
