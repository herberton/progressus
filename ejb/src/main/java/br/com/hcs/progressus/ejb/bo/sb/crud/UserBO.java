package br.com.hcs.progressus.ejb.bo.sb.crud;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hcs.progressus.client.bo.sb.crud.UserBORemote;
import br.com.hcs.progressus.ejb.bo.sb.crud.common.ProgressusBOCRUD;
import br.com.hcs.progressus.enumerator.Status;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.jpa.entity.UserEntity;

@Stateless
@LocalBean
public class UserBO extends ProgressusBOCRUD<UserEntity> implements UserBORemote {

   private static final long serialVersionUID = -3434597631856835762L;

   public UserBO() { super(); }

   public String test(String number) {
	   
	   try {
		   
		   UserEntity user = new UserEntity();
		   
		   user.setName("teste");
		   user.setStatus(Status.ACTIVE);
		   
		   user = this.save(user, false);
		   
		   user = this.select(user);
		   user.setName("teste 1");
		   
		   this.save(user, true);
		   
			System.out.println("count is " + this.count(new UserEntity()));
			
	   } catch (ProgressusException e) {
		   e.printStackTrace();
	   }
	   
	   return "number is " + number;
   }
}
