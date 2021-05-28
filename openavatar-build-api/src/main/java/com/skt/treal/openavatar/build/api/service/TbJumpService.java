package com.skt.treal.openavatar.build.api.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skt.treal.openavatar.build.api.config.OpenAvatarProperties;
import com.skt.treal.openavatar.build.api.mapper.TbJumpMapper;
import com.skt.treal.openavatar.build.api.mapper.TbJumpTemplateMapper;
import com.skt.treal.openavatar.build.api.model.domain.TbJump;
import com.skt.treal.openavatar.build.api.model.domain.TbJumpTemplate;
import com.skt.treal.openavatar.build.api.model.enums.EnFileType;
import com.skt.treal.openavatar.build.api.model.enums.EnJumpType;
import com.skt.treal.openavatar.build.api.model.enums.EnJumpWorkStatus;
import com.skt.treal.openavatar.build.api.model.enums.EnTraFileName;
import com.skt.treal.openavatar.build.api.model.vo.queue.ReqJumpBuildVO;

@Service
public class TbJumpService extends BaseService {

	@Autowired
	private RequestJumpBuildJobService reqBuildJobService;
	@Autowired
	private OpenAvatarProperties openAvatarProperties;
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
		if( items.size() != 0 ) {
			tbJumpMapper.updateAllTbJumpByWorkStatus(items);
		}
		// 요청 Id 데이터 조회
		TbJump detail = tbJumpMapper.selectTbJumpDetailByJumpId(jumpId);
		// Queue 에 보낼 메세지 Object 생성
		ReqJumpBuildVO reqJumpBuildVo = new ReqJumpBuildVO();
		reqJumpBuildVo.setPlatform( EnTraFileName.WebGL.name() );
		reqJumpBuildVo.setInputFilePath( makePath(openAvatarProperties.getStorage().getInput(), detail.getSaveJumpFilePath(), detail.getJumpId(), null) );
		reqJumpBuildVo.setOutputFilePath( makePath(openAvatarProperties.getStorage().getOutput(), detail.getWebglPath(), detail.getJumpId(), null) );
		reqJumpBuildVo.setLogFilePath( makePath(openAvatarProperties.getStorage().getOutput(), detail.getWebglPath(), detail.getJumpId(), EnFileType.LOG) );
		reqJumpBuildVo.setRename( detail.getItemkey());
		if( EnJumpType.TEMPLATE.getCode().equals(detail.getJumpType()) ) {
			TbJumpTemplate template = tbJumpTemplateMapper.selectTbJumpTemplateByTemplateId(detail.getTemplateId());
			reqJumpBuildVo.setPlatform(EnTraFileName.ALL.name());
			reqJumpBuildVo.setProcessingType( EnFileType.TEMPLATE_TRO.getCode() );
			reqJumpBuildVo.setOutputFilePath( makePath(openAvatarProperties.getStorage().getOutput(), detail.getWebglPath(), detail.getJumpId(), EnFileType.TRO ) );
			// XXX 추후 수정
			reqJumpBuildVo.setInputFilePath( "" + template.getJumpFilePath() );
			reqJumpBuildVo.setMainTexture( "" );
			// XXX 추후 수정
		}
		// Queue 에 담기
		reqBuildJobService.requestJumpBuildJob(reqJumpBuildVo);
	}
	
	private String makePath( String subPath, String saveFilePath, Integer jumpId , EnFileType enFileType  ) {
		String defaultPath = openAvatarProperties.getStorage().getDefaultPath();
		Path path = null;
		if( !ObjectUtils.isEmpty(enFileType) ) {
			path = Paths.get(defaultPath, subPath, saveFilePath, String.valueOf(jumpId), enFileType.getCode());
		} else {
			path = Paths.get(defaultPath, subPath, saveFilePath, String.valueOf(jumpId));
		}
		
		return path.toFile().getAbsolutePath();
	}
}
