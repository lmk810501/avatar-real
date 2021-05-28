package com.skt.treal.openavatar.build.api.model.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbItemTro {

	private Integer troId; 
	private Integer itemkey; 
	private String platform; 
	private String troPath;
	private Integer createUser;
	private LocalDateTime createDatetime; 
	private Integer modifyUser; 
	private LocalDateTime modifyDatetime;
	
}
