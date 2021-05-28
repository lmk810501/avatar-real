package com.skt.treal.openavatar.build.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.skt.treal.openavatar.build.api.model.domain.TbItem;

@Mapper
public interface TbItemMapper {

	TbItem selectTbItemByItemkey( Integer itemkey );
	
}
