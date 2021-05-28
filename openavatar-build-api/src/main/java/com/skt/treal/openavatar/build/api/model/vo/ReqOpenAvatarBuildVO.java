package com.skt.treal.openavatar.build.api.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqOpenAvatarBuildVO {

	@JsonProperty("jumpId")
	private Integer jumpId;

	public Integer getJumpId() {
		return jumpId;
	}

	public void setJumpId(Integer jumpId) {
		this.jumpId = jumpId;
	}

	@Override
	public String toString() {
		return "ReqOpenAvatarBuildVO [jumpId=" + jumpId + "]";
	}
	
}
