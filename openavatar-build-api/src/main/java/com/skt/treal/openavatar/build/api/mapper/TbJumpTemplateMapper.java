package com.skt.treal.openavatar.build.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.skt.treal.openavatar.build.api.model.domain.TbJumpTemplate;

@Mapper
public interface TbJumpTemplateMapper {

	TbJumpTemplate selectTbJumpTemplateByTemplateId( Integer TemplateId );
	
}
