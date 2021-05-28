package com.skt.treal.openavatar.build.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.skt.treal.openavatar.build.api.model.domain.TbItemJson;

@Mapper
public interface TbItemJsonMapper {

	TbItemJson selectTbItemJsonByJsonId( Integer jsonId );
	
}
