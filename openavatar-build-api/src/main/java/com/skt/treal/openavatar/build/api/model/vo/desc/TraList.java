package com.skt.treal.openavatar.build.api.model.vo.desc;

import java.io.Serializable;
import java.util.List;

import com.skt.treal.openavatar.build.api.model.vo.desc.vo.Tra;
import com.skt.treal.openavatar.build.api.model.vo.desc.vo.Trm;
import com.skt.treal.openavatar.build.api.model.vo.desc.vo.Unp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraList implements Serializable {

	private static final long serialVersionUID = 2938711606087923859L;
	
	private List<Tra> traList;
	private Unp unp;
	private Trm trm;
	
	@Override
	public String toString() {
		return "TraList [traList=" + traList + ", unp=" + unp + ", trm=" + trm + "]";
	}
	
}
