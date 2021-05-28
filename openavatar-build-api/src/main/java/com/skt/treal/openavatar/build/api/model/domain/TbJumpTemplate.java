package com.skt.treal.openavatar.build.api.model.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TbJumpTemplate {

	private Integer templateId; 
	private String templateName; 
	private String categorykey; 
	private String description; 
	private String jumpFile; 
	private String jumpFilePath; 
	private String thumbnail; 
	private String thumbnailPath; 
	private String useYn; 
	private String delYn; 
	private Integer createUser; 
	private LocalDateTime createDatetime;
	private Integer modifyUser; 
	private LocalDateTime modifyDatetime;

	@Override
	public String toString() {
		return "TbJumpTemplate [templateId=" + templateId + ", templateName=" + templateName + ", categorykey="
				+ categorykey + ", description=" + description + ", jumpFile=" + jumpFile + ", jumpFilePath="
				+ jumpFilePath + ", thumbnail=" + thumbnail + ", thumbnailPath=" + thumbnailPath + ", useYn=" + useYn
				+ ", delYn=" + delYn + ", createUser=" + createUser + ", createDatetime=" + createDatetime
				+ ", modifyUser=" + modifyUser + ", modifyDatetime=" + modifyDatetime + "]";
	}
	
}
