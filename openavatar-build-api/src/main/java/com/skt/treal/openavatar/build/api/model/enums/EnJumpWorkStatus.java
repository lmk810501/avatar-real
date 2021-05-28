package com.skt.treal.openavatar.build.api.model.enums;

import lombok.Getter;

@Getter
public enum EnJumpWorkStatus {

	UL ("UL", "업로드"),
	WS ("WS", "webGL 시작"),
	WC ("WC", "webGL 완료"),
	TS ("TS", "TRO 빌드 시작"),
	TC ("TC", "TRO 빌드 완료"),
	AR ("AR", "심사 반려"),
	AP ("AP", "심사 통과"),
	OS ("OS", "개시 시작"),
	OC ("OC", "개시 완료"),
	SS ("SS", "TRC 전송 시작"),
	SC ("SC", "TRC 전송 완료"),
	CC ("CC", "취소")
	;
	
	private String code;
	private String desc;
	
	private EnJumpWorkStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static EnJumpWorkStatus search( String code ) {
		for( EnJumpWorkStatus type : EnJumpWorkStatus.values() ) {
			if( type.getCode().equals( code ) )
				return type;
		}
		return null;
	}
}
