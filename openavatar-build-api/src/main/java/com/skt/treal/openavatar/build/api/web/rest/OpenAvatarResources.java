package com.skt.treal.openavatar.build.api.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.skt.treal.openavatar.build.api.model.enums.EnApiException;
import com.skt.treal.openavatar.build.api.model.exception.ApiException;
import com.skt.treal.openavatar.build.api.model.vo.ReqOpenAvatarBuildVO;
import com.skt.treal.openavatar.build.api.service.RequestReleaseJobService;
import com.skt.treal.openavatar.build.api.service.TbJumpService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api( value = "OpenAvatarResources", tags = "Open - Avatar API" )
public class OpenAvatarResources extends BaseResources {

	// Queue Service
	@Autowired
	private RequestReleaseJobService reqReleaseJobService;
	// logic service
	@Autowired
	private TbJumpService tbJumpService;
	
	@PostMapping( value = "/open-avatar/release" )
	@ApiOperation( value = "TRP(Tro) Publishing API", httpMethod = "POST", produces = "application/json" )
	public void avatarReleaseApi( HttpServletRequest request ) throws Exception {
		try {
			reqReleaseJobService.requestReleaseJob();
		} catch (Exception e) {
			throw new ApiException( EnApiException.DEFAULT, e );
		}
	}
	
	@PostMapping( value = "/open-avatar/build" )
	@ApiOperation( value = "TRO Build API", httpMethod = "POST", produces = "application/json" )
	public void jumpBuildApi( HttpServletRequest request,
			@RequestBody ReqOpenAvatarBuildVO reqOpenAvatarBuildVO ) throws Exception {
		try {
			tbJumpService.getTbJumpData(reqOpenAvatarBuildVO.getJumpId());
		} catch (Exception e) {
			throw new ApiException( EnApiException.DEFAULT, e );
		}
	}
	
	@PostMapping( value = "/open-avatar/test" )
	@ApiIgnore
	public ResponseEntity<String> callApiTestUrl( HttpServletRequest request ) {
		return new ResponseEntity<>( "호출 성공", HttpStatus.OK );
	}
}
