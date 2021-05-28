package com.skt.treal.openavatar.build.api.model.vo.queue;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqReleaseVO implements Serializable {

	private static final long serialVersionUID = -368999348154464900L;
	private Integer time;

	@Override
	public String toString() {
		return "ReqReleaseVO [time=" + time + "]";
	}
	
}
