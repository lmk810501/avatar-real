package com.skt.treal.openavatar.build.api.model.vo.desc;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileList implements Serializable {
	
	private static final long serialVersionUID = 8050547277879657618L;
	
	private List<TraList> fileList;

	@Override
	public String toString() {
		return "FileList [fileList=" + fileList + "]";
	}
	
}
