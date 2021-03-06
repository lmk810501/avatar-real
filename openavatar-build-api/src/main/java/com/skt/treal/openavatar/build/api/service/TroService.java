package com.skt.treal.openavatar.build.api.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skt.treal.openavatar.build.api.config.OpenAvatarProperties;
import com.skt.treal.openavatar.build.api.model.enums.EnFileType;
import com.skt.treal.openavatar.build.api.model.enums.EnTraFileName;
import com.skt.treal.openavatar.build.api.model.vo.desc.FileList;
import com.skt.treal.openavatar.build.api.model.vo.desc.TraList;
import com.skt.treal.openavatar.build.api.model.vo.desc.vo.Tra;
import com.skt.treal.openavatar.build.api.model.vo.desc.vo.Trm;
import com.skt.treal.openavatar.build.api.model.vo.desc.vo.Tro;
import com.skt.treal.openavatar.build.api.model.vo.desc.vo.Unp;

@Service
public class TroService extends BaseService {

	@Autowired
	private OpenAvatarProperties openAvatarProperties;
	
	public void makeTrpFile() {
		log.info("### Create TRA File Start ###");
		List<Tra> items = new ArrayList<Tra>();
		for( EnTraFileName en : EnTraFileName.values() ) {
			if( en == EnTraFileName.ALL || en == EnTraFileName.WebGL ) continue;
			List<Tro> troInfoList = makeTraFile( en );
			Tra tra = makeTraObject( troInfoList, en );
			items.add(tra);
		}
		log.info("### Create TRA File End ###");
		// unp Info
		Unp unp = new Unp();
		unp.setUnpFileName( "" );
		unp.setUnpFileSize( 0L );
		unp.setUnpVer( "" );
		// trm Info
		Trm trm = new Trm();
		trm.setTrmFileName( "" );
		trm.setTrmFileSize( 0L );
		trm.setTrmVer( "" );
		// TraList Info
		List<TraList> traListItems = new ArrayList<TraList>();
		TraList traList = new TraList();
		traList.setTraList(items);
		traList.setUnp(unp);
		traList.setTrm(trm);
		traListItems.add(traList);
		// fileList
		FileList fileList = new FileList();
		fileList.setFileList(traListItems);
		// desc.json ?????? ?????? ??? TRP ????????? ??????
		createDesc(fileList);
		createTrp();
	}
	
	private List<Tro> makeTraFile( EnTraFileName enTraFileName ) {
		// TRO File ?????? ?????????
		List<String> setFile = new ArrayList<String>();
		if( EnTraFileName.Android.equals(enTraFileName) ) {
			setFile = makeAndroidFileList();
		} else if( EnTraFileName.IOS.equals(enTraFileName) ) {
			setFile = makeIOSFileList();
		} else if( EnTraFileName.Standalone.equals(enTraFileName) ) {
			setFile = makeWindowsFileList();
		}
		// ahems Tro File ??? *.tra(zip) ????????? ??????
		List<Tro> troInfoList = new ArrayList<Tro>();
		makeTraZipFile(setFile, enTraFileName, troInfoList);
		
		return troInfoList;
	}
	
	// Tra ??? ??????
	private void makeTraZipFile( List<String> setFile, EnTraFileName enTraFileName, List<Tro> troInfoList ) {
		String zipName = enTraFileName.name() + "." + EnFileType.TRA.getCode();
		String tempPath = openAvatarProperties.getStorage().getTempZipDir();
		BufferedOutputStream bos = null;
		ZipOutputStream zout = null;
		try {
			Path zipPath = Paths.get(tempPath, EnFileType.TRA.getCode());
			File zipFilePath = zipPath.toFile();
			if( !zipFilePath.exists() ) zipFilePath.mkdirs();
			Path filePath = Paths.get(zipFilePath.getAbsolutePath(), zipName);
			bos = new BufferedOutputStream(new FileOutputStream(filePath.toFile()));
			zout = new ZipOutputStream(bos);
			for( String troFilePath : setFile ) {
				File file = new File(troFilePath);
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);
				byte[] buf = new byte[1024];
				int length;
				ZipEntry entry = new ZipEntry(file.getName());
				zout.putNextEntry(entry);
				while( (length = bis.read(buf)) > 0 ) {
					zout.write(buf, 0, length);
				}
				bis.close();
				fis.close();
				
				// Tro File Infos
				Tro tro = new Tro();
				tro.setTroID( "" );
				tro.setTroType( "" );
				tro.setTroFileName( file.getName() );
				tro.setTroFileSize( file.length() );
				tro.setTroVer( "" );
				tro.setHash( "" );
				troInfoList.add(tro);
			}
		} catch (Exception e) {
			log.error("### makeTraZipFile Method zipping Error : {} ###", e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if( zout != null ) {
					zout.close();
				}
				if( bos != null ) {
					bos.close();
				}
			} catch (IOException e) {
				log.error("### makeTraZipFile Method bos, zout close Error : {} ###", e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	// desc.json ?????? ??????
	private void createDesc( FileList fileList ) {
		log.info("### Create desc.json File Start ###");
		Path descTempPath = Paths.get(openAvatarProperties.getStorage().getTempZipDir(), EnFileType.TRA.getCode());
		File descFile = descTempPath.toFile();
		if( !descFile.exists() ) descFile.mkdirs();
		BufferedOutputStream bos = null;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			String str = objMapper.writeValueAsString(fileList);
			Path filePath = Paths.get(descFile.getAbsolutePath(), "desc.json");
			bos = new BufferedOutputStream(new FileOutputStream(filePath.toFile()));
			bos.write(str.getBytes());
		} catch (Exception e) {
			log.error("### createDescAndTrp Method Create desc.json File Error : {} ###", e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if( bos != null ) {
					bos.close();
				}
			} catch (Exception e) {
				log.error("### createDescAndTrp Method desc.json bos close Error : {} ###", e.getMessage());
				e.printStackTrace();
			}
		}
		log.info("### Create desc.json File End ###");
	}
	
	// *.trp ?????? ??????
	private void createTrp() {
		log.info("### Create *.trp File Start ###");
		String zipName = openAvatarProperties.getStorage().getTrpFileName() + "." + EnFileType.TRP.getCode();
		String tempPath = openAvatarProperties.getStorage().getTempZipDir();
		BufferedOutputStream bos = null;
		ZipOutputStream zout = null;
		try {
			Path trpTempDirPath = Paths.get(tempPath, EnFileType.TRP.getCode());
			File trpFile = trpTempDirPath.toFile();
			if( !trpFile.exists() ) trpFile.mkdirs();
			Path trpTempPath = Paths.get(trpFile.getAbsolutePath(), zipName);
			bos = new BufferedOutputStream(new FileOutputStream(trpTempPath.toFile()));
			zout = new ZipOutputStream(bos);
			Path traTempPath = Paths.get(tempPath, EnFileType.TRA.getCode());
			File[] files = traTempPath.toFile().listFiles();
			for( File file : files ) {
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);
				byte[] buf = new byte[1024];
				int length;
				ZipEntry entry = new ZipEntry(file.getName());
				zout.putNextEntry(entry);
				while( (length = bis.read(buf)) > 0 ) {
					zout.write(buf, 0, length);
				}
				bis.close();
				fis.close();
			}
		} catch (Exception e) {
			log.error("### makeTrpZipFile Method zipping Error : {} ###", e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if( zout != null ) {
					zout.close();
				}
				if( bos != null ) {
					bos.close();
				}
			} catch (IOException e) {
				log.error("### makeTrpZipFile Method bos, zout close Error : {} ###", e.getMessage());
				e.printStackTrace();
			}
		}
		log.info("### Create *.trp File End ###");
	}
	
	// Tra Info ??????
	private Tra makeTraObject( List<Tro> troInfoList, EnTraFileName enTraFileName ) {
		Path path = Paths.get(openAvatarProperties.getStorage().getTempZipDir(), EnFileType.TRA.getCode(),
				enTraFileName.name() + "." + EnFileType.TRA.getCode());
		File file = path.toFile();
		Tra tra = new Tra();
		tra.setTraOSCode( "" );
		tra.setTraVRCode( "" );
		tra.setTraStereoCode( "" );
		tra.setTraDeviceCode( "" );
		tra.setTraSDKCode( "" );
		tra.setTraOSVer( "" );
		tra.setTraFileSize(file.length());
		tra.setTraFileName(file.getName());
		tra.setTraVer( "" );
		tra.setMainTRO( "" );
		tra.setTroList(troInfoList);
		
		return tra;
	}
	
	// File ????????? ????????? - dummy data
	private List<String> makeAndroidFileList() {
		List<String> items = new ArrayList<String>();
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\1\\android_1.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\2\\android_2.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\3\\android_3.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\4\\android_4.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\5\\android_5.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\6\\android_6.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\7\\android_7.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\8\\android_8.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\9\\android_9.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\10\\android_10.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\11\\android_11.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\12\\android_12.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\13\\android_13.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\14\\android_14.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\15\\android_15.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\16\\android_16.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\17\\android_17.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\18\\android_18.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\19\\android_19.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\20\\android_20.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\21\\android_21.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\22\\android_22.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\23\\android_23.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\24\\android_24.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\25\\android_25.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\26\\android_26.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\27\\android_27.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\28\\android_28.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\29\\android_29.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Android\\30\\android_30.tro");
		
		return items;
	}
	
	private List<String> makeIOSFileList() {
		List<String> items = new ArrayList<String>();
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\1\\ios_1.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\2\\ios_2.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\3\\ios_3.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\4\\ios_4.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\5\\ios_5.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\6\\ios_6.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\7\\ios_7.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\8\\ios_8.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\9\\ios_9.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\10\\ios_10.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\11\\ios_11.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\12\\ios_12.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\13\\ios_13.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\14\\ios_14.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\15\\ios_15.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\16\\ios_16.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\17\\ios_17.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\18\\ios_18.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\19\\ios_19.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\20\\ios_20.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\21\\ios_21.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\22\\ios_22.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\23\\ios_23.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\24\\ios_24.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\25\\ios_25.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\26\\ios_26.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\27\\ios_27.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\28\\ios_28.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\29\\ios_29.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\IOS\\30\\ios_30.tro");
		
		return items;
	}
	
	private List<String> makeWindowsFileList() {
		List<String> items = new ArrayList<String>();
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\1\\windows_1.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\2\\windows_2.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\3\\windows_3.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\4\\windows_4.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\5\\windows_5.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\6\\windows_6.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\7\\windows_7.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\8\\windows_8.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\9\\windows_9.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\10\\windows_10.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\11\\windows_11.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\12\\windows_12.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\13\\windows_13.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\14\\windows_14.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\15\\windows_15.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\16\\windows_16.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\17\\windows_17.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\18\\windows_18.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\19\\windows_19.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\20\\windows_20.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\21\\windows_21.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\22\\windows_22.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\23\\windows_23.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\24\\windows_24.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\25\\windows_25.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\26\\windows_26.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\27\\windows_27.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\28\\windows_28.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\29\\windows_29.tro");
		items.add("E:\\A-OpenAvatar_tempFile\\Z-Release\\Standalone\\30\\windows_30.tro");
		
		
		return items;
	}
}
