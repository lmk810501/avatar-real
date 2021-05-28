package com.skt.treal.openavatar.build.api.model.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TbJump {

	private Integer jumpId;
	private Integer templateId;
	private String categorykey;
	private Integer itemkey;
	private String jumpType; 
	private String orgJumpFile; 
	private String saveJumpFile; 
	private String saveJumpFilePath; 
	private String webglPath; 
	private String workStatus; 
	private String returnReson;
	private String tag;
	private String delYn;
	private Integer createUser;
	private LocalDateTime createDatetime; 
	private Integer modifyUser; 
	private LocalDateTime modifyDatetime;
	
	@Override
	public String toString() {
		return "TbJump [jumpId=" + jumpId + ", templateId=" + templateId + ", categorykey=" + categorykey + ", itemkey="
				+ itemkey + ", jumpType=" + jumpType + ", orgJumpFile=" + orgJumpFile + ", saveJumpFile=" + saveJumpFile
				+ ", saveJumpFilePath=" + saveJumpFilePath + ", webglPath=" + webglPath + ", workStatus=" + workStatus
				+ ", returnReson=" + returnReson + ", tag=" + tag + ", delYn=" + delYn + ", createUser=" + createUser
				+ ", createDatetime=" + createDatetime + ", modifyUser=" + modifyUser + ", modifyDatetime="
				+ modifyDatetime + "]";
	}
	
}
