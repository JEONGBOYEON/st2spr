package com.board;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.s;

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
			request.setAttribute("mode", "created");
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
		if(pageNum!=null&&!pageNum.equals(""))
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
		
		
	public String article() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchKey==null){
			searchKey = "subject";
			searchValue = "";
		}
		
		if(request.getMethod().equalsIgnoreCase("GET"))
			searchValue = URLDecoder.decode(searchValue,"UTF-8");
		
		dao.updatetData("board.updateHitCount", num);
		
		dto = (BoardDTO)dao.getReadData("board.readData",num);
		
		if(dto==null)
			return "read-erroe";
		
		int lineSu = dto.getContent().split("\r\n").length;
		
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("searchKey", searchKey);
		hMap.put("searchValue",searchValue);
		hMap.put("num",dto.getNum());
		
		BoardDTO preDTO = (BoardDTO)dao.getReadData("board.preReadData",hMap);
		int preNum = 0;
		String preSubject = "";
		if(preDTO!=null){
			preNum = preDTO.getNum();
			preSubject = preDTO.getSubject();
		}
		
		BoardDTO nextDTO = (BoardDTO)dao.getReadData("board.nextReadData",hMap);
		int nextNum = 0;
		String nextSubject = "";
		if(nextDTO!=null){
			nextNum = nextDTO.getNum();
			nextSubject = nextDTO.getSubject();
		}
		
		String params = "pageNum=" + pageNum;
		if(!searchValue.equals("")){
			searchValue = URLEncoder.encode(searchValue,"UTF-8");
			params += "&searchKey=" + searchKey + "&searchValue=" + searchValue;
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("preNum", preNum);
		request.setAttribute("preSubject", preSubject);
		request.setAttribute("nextNum", nextNum);
		request.setAttribute("nextSubject", nextSubject);
		request.setAttribute("params", params);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchValue", searchValue);
		
		
		
		return SUCCESS;
	}


	public String updated() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");		
		
		if(searchKey==null){
			searchKey = "subject";
			searchValue = "";
		}
		
		if(request.getMethod().equalsIgnoreCase("GET"))
			searchValue = URLDecoder.decode(searchValue,"UTF-8");
		
		
		
		if(dto.getMode()==null || dto.getMode().equals("")){
			
			dto = (BoardDTO)dao.getReadData("board.readData",dto.getNum());
			
			if(dto==null)
				return "read-error";
			
			if(!searchValue.equals("")){
				searchValue = URLEncoder.encode(searchValue,"UTF-8");
			}
			
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("mode", "update");
			request.setAttribute("dto", dto);
			request.setAttribute("searchKey", searchKey);
			request.setAttribute("searchValue", searchValue);
			
			return INPUT;
		}
					
		
		dao.updatetData("board.updateData", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchValue", searchValue);
		
		System.out.println(searchKey+searchValue);
		
		return SUCCESS;
		
	}
		
	public String deleted() throws Exception{

		HttpServletRequest request = ServletActionContext.getRequest();

		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
			
		
		dao.delteData("board.deleteData",num);
		request.setAttribute("pageNum", pageNum);
		
		return SUCCESS;
	}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
