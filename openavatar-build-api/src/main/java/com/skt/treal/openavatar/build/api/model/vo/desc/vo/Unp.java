package com.skt.treal.openavatar.build.api.model.vo.desc.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Unp implements Serializable {

	private static final long serialVersionUID = 9057277242093375433L;
	
	private Long unpFileSize;
	private String unpFileName;
	private String unpVer;
	
	@Override
	public String toString() {
		return "Unp [unpFileSize=" + unpFileSize + ", unpFileName=" + unpFileName + ", unpVer=" + unpVer + "]";
	}
	
}
