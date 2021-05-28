package com.skt.treal.openavatar.build.api.model.exception;

import com.skt.treal.openavatar.build.api.model.enums.EnApiException;

import lombok.Getter;

@Getter
public class ApiException extends Exception {

	private static final long serialVersionUID = 2476111896144948096L;

	private EnApiException enException;
	private Throwable cause;

	public ApiException(EnApiException enException, Throwable cause) {
		super(enException.getDesc(), cause);
		this.enException = enException;
		this.cause = cause;
	}

	public ApiException(EnApiException enException) {
		super(enException.getDesc());
		this.enException = enException;
	}

}
