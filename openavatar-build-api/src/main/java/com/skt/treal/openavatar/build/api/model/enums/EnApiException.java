package com.skt.treal.openavatar.build.api.model.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum EnApiException {

	DEFAULT ( "TRE000", "Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR ),
	TRE001	( "TRE001", "Invalid Parameter.", HttpStatus.BAD_REQUEST ),
	TRE002	( "TRE002", "Unauthorized.", HttpStatus.UNAUTHORIZED ),
	TRE003	( "TRE003", "Not Exist Data", HttpStatus.NO_CONTENT ),
	TRE004	( "TRE004", "Does Not Match Precondition", HttpStatus.PRECONDITION_FAILED ),
	TRE005	( "TRE005", "Permission denied", HttpStatus.FORBIDDEN )
	;

	private String code;
	private String desc;
	private HttpStatus httpstatus;

	private EnApiException( String code, String desc, HttpStatus httpstatus ) {
		this.code = code;
		this.desc = desc;
		this.httpstatus = httpstatus;
	}

	public static EnApiException search( String code ) {
		for( EnApiException type : EnApiException.values() ) {
			if( type.getCode().equals( code ) )
				return type;
		}
		return EnApiException.DEFAULT;
	}

}
