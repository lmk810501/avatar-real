package com.skt.treal.openavatar.build.api.model.vo.desc.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Trm implements Serializable {

	private static final long serialVersionUID = 2981477813296171735L;
	
	private Long trmFileSize;
	private String trmFileName;
	private String trmVer;
	
	@Override
	public String toString() {
		return "Trm [trmFileSize=" + trmFileSize + ", trmFileName=" + trmFileName + ", trmVer=" + trmVer + "]";
	}
	
}
