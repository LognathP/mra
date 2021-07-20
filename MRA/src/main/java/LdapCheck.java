import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapCheck {

	public static void main(String[] args) {
		
	
		String domain = "stcdemo.in";
		String dn = "user1@"+domain;
		String ldapurl = "ldap://10.10.2.51";
		String password = "Test@123";
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapurl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, dn); 
		env.put(Context.SECURITY_CREDENTIALS, password);
		
		try {
			DirContext authCont = new InitialDirContext(env);
			System.out.println("Connected");
		} catch (NamingException e) {
			System.out.println("Connection Failed");
			e.printStackTrace();
		}
		
	}
}
