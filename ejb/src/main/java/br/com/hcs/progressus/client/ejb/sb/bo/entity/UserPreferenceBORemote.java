package br.com.hcs.progressus.client.ejb.sb.bo.entity;

import javax.ejb.Remote;

import br.com.hcs.progressus.server.jpa.entity.UserPreferenceEntity;

@Remote
public interface UserPreferenceBORemote extends ProgressusBOEntityRemote<UserPreferenceEntity> 
{

}
