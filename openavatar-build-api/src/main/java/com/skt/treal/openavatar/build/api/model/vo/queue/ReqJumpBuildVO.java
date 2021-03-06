package com.skt.treal.openavatar.build.api.model.vo.queue;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqJumpBuildVO implements Serializable {

	private static final long serialVersionUID = -6090868601403540344L;

	private String platform;
	private String inputFilePath;
	private String outputFilePath;
	private String logFilePath;
	private String processingType;
	private Integer rename;
	private String mainTexture;
	
	@Override
	public String toString() {
		return "ReqJumpBuildVO [platform=" + platform + ", inputFilePath=" + inputFilePath + ", outputFilePath="
				+ outputFilePath + ", logFilePath=" + logFilePath + ", processingType=" + processingType + ", rename="
				+ rename + ", mainTexture=" + mainTexture + "]";
	}
	
}
