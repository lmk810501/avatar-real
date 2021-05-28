package com.skt.treal.openavatar.build.api.model.vo.desc.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tra implements Serializable {

	private static final long serialVersionUID = 5105297903292005518L;
	
	private String traOSCode;
	private String traVRCode;
	private String traStereoCode;
	private String traDeviceCode;
	private String traSDKCode;
	private String traOSVer;
	private Long traFileSize;
	private String traFileName;
	private String traVer;
	private String mainTRO;
	private List<Tro> troList;
	
	@Override
	public String toString() {
		return "Tra [traOSCode=" + traOSCode + ", traVRCode=" + traVRCode + ", traStereoCode=" + traStereoCode
				+ ", traDeviceCode=" + traDeviceCode + ", traSDKCode=" + traSDKCode + ", traOSVer=" + traOSVer
				+ ", traFileSize=" + traFileSize + ", traFileName=" + traFileName + ", traVer=" + traVer + ", mainTRO="
				+ mainTRO + ", troList=" + troList + "]";
	}
	
}
