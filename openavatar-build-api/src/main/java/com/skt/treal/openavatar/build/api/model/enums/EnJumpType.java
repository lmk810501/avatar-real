package com.skt.treal.openavatar.build.api.model.enums;

import lombok.Getter;

@Getter
public enum EnJumpType {

	JUMP		("J", "jump 3D 파일"),
	TEMPLATE	("T", "템플릿 이미지 파일")
	;
	
	private String code;
	private String desc;
	
	private EnJumpType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static EnJumpType search( String code ) {
		for( EnJumpType type : EnJumpType.values() ) {
			if( type.getCode().equals( code ) )
				return type;
		}
		return null;
	}
}
