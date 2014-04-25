package br.com.hcs.progressus.server.ejb.sb.bo.entity;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.UserPreferenceBORemote;
import br.com.hcs.progressus.server.jpa.entity.UserPreferenceEntity;

@NoArgsConstructor
@Stateless
public class UserPreferenceBO extends ProgressusBOEntity<UserPreferenceEntity> implements UserPreferenceBORemote {
	private static final long serialVersionUID = 5958682885967509852L;
}
