package br.com.hcs.progressus.server.ejb.sb.dao;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.dao.UserPreferenceDAOLocal;
import br.com.hcs.progressus.server.jpa.entity.UserPreferenceEntity;

@NoArgsConstructor
@Stateless
public class UserPreferenceDAO extends ProgressusDAO<UserPreferenceEntity> implements UserPreferenceDAOLocal {
	private static final long serialVersionUID = -4749719413932168441L;
}
