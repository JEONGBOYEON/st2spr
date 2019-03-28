package com.util;

import org.springframework.stereotype.Service;

@Service("myUtil")
public class MyUtil {
	
	/* 전체 페이지수 구하기
	1.numPerPage : 한 화면에 표시할 데이터 갯수
	2.dataCount : 전체 데이터 갯수   
	*/
	public int getPageCount(int numPerPage, int dataCount){
		
		int pageCount = 0;
		
		pageCount = dataCount / numPerPage;
		
		if(dataCount % numPerPage !=0)
			pageCount++;
		
		return pageCount;
	}
	
	/*페이징 처리
	1.currentPage : 현재 표시할 페이지
	2.totalPage : 전체 페이지 수
	3.listUrl : 링크를 설정할 URL
	*/
	public String pageIndexList(int currentPage, int totalPage, String listUrl){
		
		int numPerBlock = 5;	//◀ --- ▶ 사이 숫자 갯수
		int currentPageSetup;	//◀가 가지고 있어야 할 숫자
		int page;
		
		StringBuffer sb = new StringBuffer();
		
		if(currentPage==0 || totalPage==0)
			return "";
		
		//list.jsp?pageNum=9
		//list.jsp?searchKey=name&seacrhValue=suzi&pageNum=9
		if(listUrl.indexOf("?")!=-1){
			listUrl = listUrl+ "&";
		}else{
			listUrl = listUrl+ "?";
		}
		
		//표시할 첫페이지에서 -1한값
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock;
		
		if(currentPage % numPerBlock == 0)
			currentPageSetup = currentPageSetup - numPerBlock;
		
		//◀이전
		if(totalPage>numPerBlock && currentPageSetup>0){
			
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + 
					"\">◀이전</a>&nbsp;");
			
			//<a href="list.jsp?pageNum=5">◀이전</a>&nbsp;			
		}
		
		//바로가기 페이지
		page = currentPageSetup + 1;
		
		while(page<=totalPage && page<=(currentPageSetup+numPerBlock)) {
			
			if(page == currentPage){
				sb.append("<font color=\"Fuchsia\"><b>" + page + "</b></font>&nbsp;");
			}else{
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + 
						"\">" + page + "</a>&nbsp;");
			}
			page++;
		}
		
		//▶다음
		if(totalPage-currentPageSetup > numPerBlock){
			sb.append("<a href=\"" + listUrl + "pageNum=" + page + 
					"\">▶다음</a>&nbsp;");
		}
		
		return sb.toString();
		
	}
	
}
