package com.ldap;

import com.ldap.Person;
import java.security.MessageDigest;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

/**
 *  com.ldap.LDAPUtil
 *
 *  @author raghu maghu
 */
public class LDAPUtil {

    private Logger logger = Logger.getLogger(LDAPUtil.class);
    private Hashtable<String, String> env = new Hashtable<String, String>();

    public LDAPUtil() {
        try {
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://localhost:389");
            //env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
            //env.put(Context.SECURITY_CREDENTIALS, "secret");
        } catch (Exception e) {
            e.printStackTrace();
            }

    }

    private boolean insert(Person person) {
        try {

            DirContext dctx = new InitialDirContext(env);
            Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("uid", person.getName()));
            matchAttrs.put(new BasicAttribute("cn", person.getName()));
            matchAttrs.put(new BasicAttribute("street", person.getAddress()));
            matchAttrs.put(new BasicAttribute("sn", person.getName()));
            matchAttrs.put(new BasicAttribute("userpassword", encryptLdapPassword("SHA", person.getPassword())));
            matchAttrs.put(new BasicAttribute("objectclass", "top"));
            matchAttrs.put(new BasicAttribute("objectclass", "person"));
            matchAttrs.put(new BasicAttribute("objectclass", "organizationalPerson"));
            matchAttrs.put(new BasicAttribute("objectclass", "inetorgperson"));
            String name = "uid=" + person.getName() + ",ou=people,dc=example,dc=com";
            InitialDirContext iniDirContext = (InitialDirContext) dctx;
            iniDirContext.bind(name, dctx, matchAttrs);

            System.out.println("success inserting "+person.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean edit(Person person) {
        try {

            DirContext ctx = new InitialDirContext(env);
            ModificationItem[] mods = new ModificationItem[2];
            Attribute mod0 = new BasicAttribute("street", person.getAddress());
            Attribute mod1 = new BasicAttribute("userpassword", encryptLdapPassword("SHA", person.getPassword()));
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);

            ctx.modifyAttributes("uid=" + person.getName() + ",ou=people,dc=example,dc=com", mods);

            logger.debug("success editing "+person.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public LdapResult searchRecord(Person p)  {        
    	logger.info("Entering search Login ::");
        //Hashtable<String, String>  env = new Hashtable<String, String>();
        //env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    	LdapResult lr = new LdapResult();
    	lr.setResultCode(99);
    	lr.setResultDesc("Invalid user ID or Password");
    	
        try {
          DirContext dctx = new InitialDirContext(env);
                 
          SearchControls sc = new SearchControls();
          String[] attributeFilter = {"street", "sn"};
          sc.setReturningAttributes(attributeFilter);
          sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
          String base = "ou=people,dc=example,dc=com";
     
          // The following resolves to (&(sn=S*)(userPassword=*))      
          String filter = "(&(uid=" + p.getName() + ")(userPassword=" + encryptLdapPassword("SHA", p.getPassword()) + "))";
          //String filter = "(&(uid=" + p.getName() + ")(userPassword=" +  p.getPassword() + "))";
          //String filter = "(&(uid=" + p.getName() + "))"; 
     
      	  System.out.println("Search filter is ::"+ filter);
          NamingEnumeration<?> results = dctx.search(base, filter, sc);
          
          
          while (results.hasMore()) {
        	  lr.setResultCode(0);
        	  lr.setResultDesc("User successfully authenticated");
            SearchResult sr = (SearchResult) results.next();
            Attributes attrs = sr.getAttributes();
            Attribute attr = attrs.get("street");
            System.out.println(attr.get());
            attr = attrs.get("sn");
            System.out.println(attr.get());
            p.setName(attr.get().toString());
            break;
          }
          dctx.close();
        } catch (Exception e) {
        	lr.setResultCode(99);
        	lr.setResultDesc("Invalid user ID or Password");
        	e.printStackTrace();
        }
        return lr;
      }

    
    private boolean delete(Person person) {
        try {

            DirContext ctx = new InitialDirContext(env);
            ctx.destroySubcontext("uid=" + person.getName() + ",ou=people,dc=example,dc=com");

            logger.debug("success deleting "+person.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean search(Person person) {
        try {

            DirContext ctx = new InitialDirContext(env);
            String base = "ou=people,dc=example,dc=com";

            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String filter = "(&(objectclass=person)(uid="+person.getName()+"))";

            NamingEnumeration results = ctx.search(base, filter, sc);


            while (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();

                Attribute attr = attrs.get("uid");
                if(attr != null)
                    System.out.println("record found "+attr.get());
            }
            ctx.close();
                        
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String encryptLdapPassword(String algorithm, String _password) {
        String sEncrypted = _password;
        if ((_password != null) && (_password.length() > 0)) {
            boolean bMD5 = algorithm.equalsIgnoreCase("MD5");
            boolean bSHA = algorithm.equalsIgnoreCase("SHA")
                    || algorithm.equalsIgnoreCase("SHA1")
                    || algorithm.equalsIgnoreCase("SHA-1");
            if (bSHA || bMD5) {
                String sAlgorithm = "MD5";
                if (bSHA) {
                    sAlgorithm = "SHA";
                }
                try {
                    MessageDigest md = MessageDigest.getInstance(sAlgorithm);
                    md.update(_password.getBytes("UTF-8"));
                    sEncrypted = "{" + sAlgorithm + "}" + (new BASE64Encoder()).encode(md.digest());
                } catch (Exception e) {
                    sEncrypted = null;
                    e.printStackTrace();
                }
            }
        }
        return sEncrypted;
    }

    public static void main(String[] args) {
        LDAPUtil main = new LDAPUtil();

        Person person = new Person();
        person.setAddress("Alankar");
        person.setName("Alankar the star");
        person.setPassword("alankar123");
        //System.exit(0);
        // insert
        main.insert(person);
        
        // edit
        //main.edit(person);
        
        // select
        //main.search(person);
        //main.searchRecord(person);
        // delete
        //main.delete(person);
    }
}
