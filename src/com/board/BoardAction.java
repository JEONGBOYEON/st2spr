package com.board;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.util.MyUtil;
import com.util.dao.CommonDAO;

public class BoardAction extends ActionSupport implements Preparable,ModelDriven<BoardDTO>{

	private static final long serialVersionUID = 1L;

	//원래는 게터를 만들어야 하는데 이번에 안만들고 setAttribute로 사용할꺼임
	private BoardDTO dto;
	
	
	//매번 변수마다 CommonDAO dao = ~~이걸 어노테이션을 사용해서 간편하게
	@Resource(name="dao")
	private CommonDAO dao;
	
	@Resource(name="myUtil")
	private MyUtil myUtil;
	
	
	@Override
	public BoardDTO getModel() {
		return dto;
	}

	@Override
	public void prepare() throws Exception {
		dto = new BoardDTO();
	}
	
	public String created() throws Exception{
		
		System.out.println(dto.getMode());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(dto==null||dto.getMode()==null||dto.getMode().equals("")){
			return INPUT;
		}
		
		int numMax = dao.getIntValue("board.numMax");
		
		dto.setNum(numMax+1);
		dto.setIpAddr(request.getRemoteAddr());
		
		dao.insertData("board.insertData", dto);
		
		return SUCCESS;
	}
	

	public String list() throws Exception{

		HttpServletRequest request = ServletActionContext.getRequest();
		
		String cp = request.getContextPath();
		
		int numPerPage = 5;
		int totalPage = 0;
		int totalDataCount = 0;
		
		String pageNum = request.getParameter("pageNum");
		int currentPage = 1;
		if(pageNum!=null)
			currentPage = Integer.parseInt(pageNum);

		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchKey==null){
			searchKey = "subject";
			searchValue = "";
		}
		
		Map<String, Object> hMap = new HashMap<String, Object>();
		
		hMap.put("searchKey",searchKey);
		hMap.put("searchValue",searchValue);
		
		totalDataCount = dao.getIntValue("board.dataCount",hMap);
		
		if(totalDataCount!=0)
			totalPage = myUtil.getPageCount(numPerPage, totalDataCount);
		
		if(currentPage>totalPage)
			currentPage = totalPage;
		
		int start = (currentPage-1)*numPerPage + 1;
		int end = currentPage*numPerPage;
		
		hMap.put("start", start);
		hMap.put("end", end);
		
		List<Object> lists = (List<Object>)dao.getListData("board.listData", hMap);
		
		//번호 재정렬
		int listNum,n=0;
		
		ListIterator<Object> it = lists.listIterator();

		while(it.hasNext()){
			
			BoardDTO vo = (BoardDTO)it.next();
			listNum = totalDataCount - (start+n-1);
			vo.setListNum(listNum);
			n++;
		}
		

		String params = "";
		String urlArticle = "";
		String urlList = "";
		
		//검색한것이 있다면
		if(!searchValue.equals("")){
			
			searchValue = URLEncoder.encode(searchValue,"UTF-8");
			
			params += "searchKey="+searchKey;
			params += "&searchValue="+searchValue;
			
		}
		
		urlList += cp +"/bbs/list.action";
		urlArticle += cp + "/bbs/article.action?pageNum="+currentPage;
		
		if(!params.equals("")){
			urlList += "?" + params;
			urlArticle += "&" + params;
		}
		
		request.setAttribute("lists", lists);
		request.setAttribute("totalDataCount", totalDataCount);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		request.setAttribute("urlArticle", urlArticle);
		request.setAttribute("pageNum", currentPage);
		
		return SUCCESS;
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
