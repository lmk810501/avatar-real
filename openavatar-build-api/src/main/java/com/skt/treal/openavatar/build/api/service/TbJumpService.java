package com.skt.treal.openavatar.build.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skt.treal.openavatar.build.api.model.domain.TbJump;
import com.skt.treal.openavatar.build.api.model.domain.TbJumpTemplate;
import com.skt.treal.openavatar.build.api.model.enums.EnFileType;
import com.skt.treal.openavatar.build.api.model.enums.EnJumpType;
import com.skt.treal.openavatar.build.api.model.enums.EnJumpWorkStatus;
import com.skt.treal.openavatar.build.api.model.enums.EnTraFileName;
import com.skt.treal.openavatar.build.api.model.vo.queue.ReqJumpBuildVO;
import com.skt.treal.openavatar.build.api.persist.gen.mapper.TbJumpMapper;
import com.skt.treal.openavatar.build.api.persist.gen.mapper.TbJumpTemplateMapper;

@Service
public class TbJumpService extends BaseService {

	@Autowired
	private RequestJumpBuildJobService reqBuildJobService;
	@Autowired
	private TbJumpMapper tbJumpMapper;
	@Autowired
	private TbJumpTemplateMapper tbJumpTemplateMapper;
	
	public void getTbJumpData( Integer jumpId ) throws Exception {
		// 요청 Id 를 제외한 데이터의 work_status == CC 로 변경
		TbJump tbJump = new TbJump();
		tbJump.setJumpId(jumpId);
		tbJump.setWorkStatus(EnJumpWorkStatus.CC.getCode());
		List<TbJump> items = tbJumpMapper.selectTbJumpByJumpIdAndWorkStatus( tbJump );
		items.stream().forEach(item -> {
			item.setWorkStatus(EnJumpWorkStatus.CC.getCode());
		});
		tbJumpMapper.updateAllTbJumpByWorkStatus(items);
		// 요청 Id 데이터 조회
		TbJump detail = tbJumpMapper.selectTbJumpDetailByJumpId(jumpId);
		// Queue 에 보낼 메세지 Object 생성
		ReqJumpBuildVO reqJumpBuildVo = new ReqJumpBuildVO();
		reqJumpBuildVo.setPlatform(EnTraFileName.WebGL.name());
		reqJumpBuildVo.setInputFilePath(null);
		reqJumpBuildVo.setOutputFilePath(null);
		reqJumpBuildVo.setLogFilePath(null);
		reqJumpBuildVo.setRename(null);
		if( EnJumpType.TEMPLATE.getCode().equals(detail.getJumpType()) ) {
			TbJumpTemplate template = tbJumpTemplateMapper.selectTbJumpTemplateByTemplateId(detail.getTemplateId());
			reqJumpBuildVo.setPlatform(EnTraFileName.ALL.name());
			reqJumpBuildVo.setInputFilePath(template.getJumpFilePath());
			reqJumpBuildVo.setProcessingType( EnFileType.TEMPLATE_TRO.getCode() );
			reqJumpBuildVo.setMainTexture( "" );
		}
//		// Queue 에 담기
//		reqBuildJobService.requestJumpBuildJob(reqJumpBuildVo);
	}
	
}
