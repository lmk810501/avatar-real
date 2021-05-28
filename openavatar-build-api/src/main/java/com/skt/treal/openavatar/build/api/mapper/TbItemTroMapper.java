package com.skt.treal.openavatar.build.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.skt.treal.openavatar.build.api.model.domain.TbItemTro;

@Mapper
public interface TbItemTroMapper {

	TbItemTro selectTbItemTroByTroId( Integer troId );
	
}
