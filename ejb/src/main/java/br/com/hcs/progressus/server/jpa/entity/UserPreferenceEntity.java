package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.SupportedLocale;
import br.com.hcs.progressus.enumerator.Template;
import br.com.hcs.progressus.enumerator.Theme;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Entity
public class UserPreferenceEntity 
	extends 
		ProgressusEntity<UserPreferenceEntity> 
	implements 
		Serializable
{
	private static final long serialVersionUID = -3583041610093606535L;
	
	
	@Setter
	@Enumerated
	private Template template;
	@Setter
	@Enumerated
	private Theme theme;
	@Setter
	@Enumerated
	private SupportedLocale supportedLocale;
	
	
	public SupportedLocale getSupportedLocale() {
		try {
			if (this.supportedLocale == null) {
				this.setSupportedLocale(SupportedLocale.getDefault());
			}
			return this.supportedLocale;
		} catch (Exception e) {
			UserPreferenceEntity.log.error(e.getMessage(), e);
		}
		return SupportedLocale.getDefault();
	}
	
	public Template getTemplate() {
		try {
			if (this.template == null) {
				this.template = Template.getDefault();
			}
			return this.template;
		} catch (Exception e) {
			UserPreferenceEntity.log.error(e.getMessage(), e);
		}
		return Template.getDefault();
	}
	
	public Theme getTheme() {
		try {
			if (this.theme == null) {
				this.setTheme(this.getTemplate().getDefaultTheme());
			}
			return this.theme;
		} catch (Exception e) {
			UserPreferenceEntity.log.error(e.getMessage(), e);
		}
		return this.getTemplate().getDefaultTheme();
	}
	
	
	public static final UserPreferenceEntity getDefault() {
		return new UserPreferenceEntity(Template.getDefault(), Template.getDefault().getDefaultTheme(), SupportedLocale.getDefault());
	}
}
