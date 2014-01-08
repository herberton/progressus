package br.com.hcs.progressus.ui.jsf;

import javax.ejb.EJB;

import br.com.hcs.progressus.ejb.client.bo.sb.TesteBORemote;

/**
 * Hello world!
 *
 */
public class App 
{
	@EJB
	public TesteBORemote teste;
	
    public static void main( String[] args )
    {
    	
    	System.out.println( "Hello World! " + new App().teste);
    }
}
