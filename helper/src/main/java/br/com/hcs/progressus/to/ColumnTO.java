package br.com.hcs.progressus.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
public class ColumnTO extends ProgressusTO<ColumnTO> {

	private static final long serialVersionUID = -7633486673331699872L;
	
	@Getter
	@Setter
	@NonNull
	private String header;
	@Getter
	@Setter
	@NonNull
	private String property;  
}
