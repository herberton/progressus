package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.Separator;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ItemMenuEntity extends ProgressusEntity<ItemMenuEntity> implements Serializable {

	private static final long serialVersionUID = -464900830512690449L;

	
	@Setter
	@Column(nullable=false)
	private String icon; 
	@Getter
	@Setter
	@Enumerated
	private Separator separator;
	@Getter
	@Setter
	@ManyToOne
	private MenuEntity parentMenu;
	@Getter
	@OneToOne(cascade=CascadeType.ALL)
	private ViewEntity view;
	
	
	public String getIcon() {
		
		try {
			
			if (StringHelper.isNullOrEmpty(this.icon)) {
				this.setIcon(Setting.WEB_ITEM_MENU_ICON_FORMAT.toString().replace("[item_menu_name]", "default"));
			}
			
			return this.icon;
			
		} catch (Exception e) {
			ItemMenuEntity.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	public void setView(ViewEntity view) {
		
		try {
		
			if (this.view == null && view == null) {
				return;
			}	
			
			if(this.view != null && view == null) {
				this.view = null;
				return;
			}		
			
			if (!view.equals(this.view)) {
				
				this.view = view;
				
				if (this.getView() != null) {
					this.getView().setItemMenu(this);	
				}
			}
		
		} catch (Exception e) {
			ItemMenuEntity.log.error(e.getMessage(), e);
		}
	}
	
	
	@Override
	public boolean equals(Object other) {
		
		try {
			
			if (other == null) {
				return false;
			}
			
			if (other == this) {
				return true;
			}
			
			if (!(other instanceof ItemMenuEntity)) {
				return false;
			}
			
			return this.getView().equals(((ItemMenuEntity)other).getView());
			
		} catch (Exception e) {
			ItemMenuEntity.log.error(e.getMessage(), e);
		}
		
		return super.equals(other);
	}
	
	@Override
	public int compareTo(ItemMenuEntity other) {
		
		try {
			
			if (other == null) {
				return -1;
			}
			
			if (other.getView() != null && this.getView() != null) {
				return this.getView().compareTo(other.getView());
			}
			
		} catch (Exception e) {
			ItemMenuEntity.log.error(e.getMessage(), e);
		}
		
		return super.compareTo(other);
	}
}
