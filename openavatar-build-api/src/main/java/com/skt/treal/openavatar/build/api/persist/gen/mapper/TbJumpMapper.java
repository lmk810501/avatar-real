package com.skt.treal.openavatar.build.api.persist.gen.mapper;

import java.util.List;

import com.skt.treal.openavatar.build.api.model.domain.TbJump;

public interface TbJumpMapper {

	List<TbJump> selectAllTbJump( TbJump tbJump );
	
	List<TbJump> selectTbJumpByJumpIdAndWorkStatus( TbJump tbJump );
	
	TbJump selectTbJumpDetailByJumpId( Integer jumpId );
	
	void updateAllTbJumpByWorkStatus( List<TbJump> list );
}
