package com.skt.treal.openavatar.build.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.skt.treal.openavatar.build.api.model.domain.TbJump;

@Mapper
public interface TbJumpMapper {

	List<TbJump> selectAllTbJump( TbJump tbJump );
	
	List<TbJump> selectTbJumpByJumpIdAndWorkStatus( TbJump tbJump );
	
	TbJump selectTbJumpDetailByJumpId( Integer jumpId );
	
	void updateAllTbJumpByWorkStatus( List<TbJump> list );
}
