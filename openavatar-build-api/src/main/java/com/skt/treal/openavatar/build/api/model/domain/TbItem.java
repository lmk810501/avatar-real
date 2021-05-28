package com.skt.treal.openavatar.build.api.model.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbItem {

	private Integer itemkey;
	private String typekey;
	private String categorykey;
	private Integer projectId;
	private String itemType;
	private Integer classId;
	private String name;
	private String saveJumpFilePath;
	private String webglPath;
	private String thumbnail;
	private String thumbnailPath;
	private LocalDateTime uploadDatetime;
	private Integer price;
	private String saleEventJoinYn;
	private String marketRegistrationYn;
	private String tag;
	private String useYn;
	private String delYn;
	private Integer createUser;
	private LocalDateTime createDatetime;
	private Integer modifyUser;
	private LocalDateTime modifyDatetime;
	
}
