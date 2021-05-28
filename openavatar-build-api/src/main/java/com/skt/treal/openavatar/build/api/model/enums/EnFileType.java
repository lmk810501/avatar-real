package com.skt.treal.openavatar.build.api.model.enums;

import lombok.Getter;

@Getter
public enum EnFileType {

	TEMPLATE_TRO	( "template_tro" ),
	TRO		("tro"), 
	TRA		("tra"), 
	TRP		("trp"),
	LOG		("log")
	;
	
	private String code;

	private EnFileType(String code) {
		this.code = code;
	}
	
	public static String searchCode( String code ) {
		for( EnFileType type : EnFileType.values() ) {
			if( type.name().equals(code) ) {
				return type.getCode();
			}
		}
		return null;
	}
}
