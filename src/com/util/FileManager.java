package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;


@Service("fileManager")
public class FileManager {

	//1. upload
	
	//path:������ ������ ���
	//return:������ ������ ���ο� �����̸�
	//���� ���ε� �Ŀ� ���ϰ� = FormFile uploadFile���� A.txt(originalFileName)�� �ָ� ���ο� �̸�(saveFileName)�� ���ϰ����� ��ȯ�ȴ�
	public static String doFileUpload(File file,String originalFileName,String path) throws Exception{
		
		String newFileName = null;
		
		if(file==null)
			return null;
		
		if(originalFileName.equals(""))
			return null;
		
		//originalFileName���� Ȯ���� �и��ؼ� Ȯ���ڸ� ����
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		if(fileExt==null||fileExt.equals(""))
			return null;
		
		//������ ������ ���ϸ� ���� = �ߺ����� �ʵ��� �̸� ����
		//�⵵,��,��¥,�ð�,��,��
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		//10�� -9���� ��
		newFileName += System.nanoTime();
		newFileName += fileExt;
		
		File f = new File(path);
		
		if(!f.exists())
			f.mkdirs();
		
		String fullFilePath = path + File.separator + newFileName;
		
		//������ ���� ���ε�(���ݱ����� �غ��۾�)
		//byte[] fileData = uploadFile.getFileData(); : �ڵ��̿��µ� �������� 
		FileInputStream fis = new FileInputStream(file);
		
		FileOutputStream fos = new FileOutputStream(fullFilePath);
		
		int byteRead = 0;
		byte[] buffer = new byte[1024];
		
		while((byteRead=fis.read(buffer,0,1024))!=-1){
			fos.write(buffer,0,byteRead);	
		}
		
		fos.close();
		fis.close();
		
		//db�� ������ saveFileName�� �������ֱ�
		return newFileName;
				
	}
	
	
	
	//2.���� ����
	
	public static void doFileDelete(String fileName,String path){
		
		String fullFilePath = path + File.separator + fileName;
		
		File f = new File(fullFilePath);
		
		if(f.exists())
			f.delete();

	}
	
	
	//3.���� �ٿ�ε�
	//saveFileName : ������ ����� ���ϸ�
	//originalFileName : Ŭ���̾�Ʈ�� ���ε��� ���ϸ�
	//path : ������ ������ ����� ���
	
	public static boolean doFileDownload(String saveFileName,String originalFileName,
			String path, HttpServletResponse response){
		
		String fullFilePath = path + File.separator + saveFileName;
		
		//�ѱ��̸� ������ �ʵ��� ����
		try {
			
			if(originalFileName==null||originalFileName.equals("")){
				originalFileName = saveFileName;
			}
			
			originalFileName = new String(originalFileName.getBytes("euc-kr"),"8859_1");
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		//�ٿ�ε�
		try {
			
			File f = new File(fullFilePath);
			
			if(f.exists()){
				
				byte readByte[] = new byte[4096];
				
				response.setContentType("application/octet-stream");
				
				response.setHeader("Content-disposition", "attachment;fileName=" + originalFileName);
				
				//�о�帮��
				BufferedInputStream fis = new BufferedInputStream(new FileInputStream(f));
				//��������
				OutputStream os = response.getOutputStream();
				
				int read;
				while((read=fis.read(readByte,0,4096))!=-1){
					os.write(readByte,0,read);
				}
				
				os.flush();
				os.close();
				fis.close();
				
				return true;
			}
			
		} catch (Exception e) {
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	
	
	
}
