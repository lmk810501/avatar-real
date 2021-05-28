package com.skt.treal.openavatar.build.api.model.vo.desc.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tro implements Serializable {

	private static final long serialVersionUID = 4753905791615606988L;
	
	private String troID;
	private String troType;
	private Long troFileSize;
	private String troFileName;
	private String troVer;
	private String hash;

	@Override
	public String toString() {
		return "Tro [troID=" + troID + ", troType=" + troType + ", troFileSize=" + troFileSize + ", troFileName="
				+ troFileName + ", troVer=" + troVer + ", hash=" + hash + "]";
	}
	
}
