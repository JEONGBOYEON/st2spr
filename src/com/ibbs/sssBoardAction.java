package com.ibbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.h;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.util.MyUtil;
import com.util.dao.CommonDAO;
import com.util.dao.CommonDAOImpl;

public class sssBoardAction extends ActionSupport implements Preparable,ModelDriven<sssBoardDTO>{

	private static final long serialVersionUID = 1L;
	
	private sssBoardDTO dto;
	public sssBoardDTO getDto() {
		return dto;
	}

	//dto가 넘어가는 부분1
	@Override
	public sssBoardDTO getModel() {
		return dto;
	}

	//dto가 넘어가는 부분2
	@Override
	public void prepare() throws Exception {
		dto = new sssBoardDTO();
	}
	

	public String created() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//입력창
		if(dto==null || dto.getMode()==null || dto.getMode().equals("")){
			request.setAttribute("mode", "created");
			return INPUT;
		}
		
		//게시물 저장
		
		CommonDAO dao = CommonDAOImpl.getInstance();

		//입력안받은 값들 직접 입력(게시글일 경우 - 댓글말고)
		int maxBoardNum = dao.getIntValue("board.maxBoardNum");
		
		dto.setBoardNum(maxBoardNum+1);
		dto.setIpAddr(request.getRemoteAddr());
		dto.setGroupNum(dto.getBoardNum());
		dto.setDepth(0);
		dto.setOrderNo(0);
		dto.setParent(0);
		
		//db에 insert
		dao.insertData("board.insertData", dto);
		
		return SUCCESS;
	}
	
	public String updated() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		CommonDAO dao = CommonDAOImpl.getInstance();

		String searchKey = dto.getSearchKey();
		String searchValue = dto.getSearchValue();

		
		//수정창 띄우기
		if(dto.getMode()==null||dto.getMode().equals("")){
			
			dto = (sssBoardDTO)dao.getReadData("board.readData",dto.getBoardNum());
			
			if(dto==null){
				return "read-error";
			}
			
			
			request.setAttribute("mode", "updated");
			request.setAttribute("pageNum", dto.getPageNum());
			request.setAttribute("searchKey", searchKey);
			request.setAttribute("searchValue", searchValue);
			
			
			return INPUT;
			
		}
		
		//실제 수정 하기
		dao.updatetData("board.updateData", dto);

		return SUCCESS;
		
	}
	
	public String reply() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		CommonDAO dao = CommonDAOImpl.getInstance();
		
		String searchKey = dto.getSearchKey();
		String searchValue = dto.getSearchValue();
		
		//답변창 띄우기
		if(dto.getMode()==null||dto.getMode().equals("")){
			
			dto = (sssBoardDTO)dao.getReadData("board.readData",dto.getBoardNum());
			
			if(dto==null){
				return "read-error";
			}
			
			String temp = "\r\n\r\n------------------------------------------\r\n\r\n";
			
			temp += "[답변]\r\n";

			dto.setSubject("[답변]"+dto.getSubject());
			dto.setContent(dto.getContent()+temp);
			dto.setName("");
			dto.setEmail("");
			dto.setPwd("");
			
			
			request.setAttribute("mode", "reply");
			request.setAttribute("pageNum", dto.getPageNum());

			request.setAttribute("searchKey", searchKey);
			request.setAttribute("searchValue", searchValue);
			
			return INPUT;
			
		}
		
		//실제 답변 하기
	
		//orderNo 변경
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("groupNum", dto.getGroupNum());
		hMap.put("orderNo", dto.getOrderNo());
		
		dao.updatetData("board.orderNoUpdate", hMap); 
		
		//답변 insert
		//1.
		int maxBoardNum = dao.getIntValue("board.maxBoardNum");
		
		dto.setBoardNum(maxBoardNum+1);
		dto.setIpAddr(request.getRemoteAddr());
		//depth,orderNo은 부모의 depth+1,orderNo
		dto.setDepth(dto.getDepth()+1);
		dto.setOrderNo(dto.getOrderNo()+1);
		
		dao.insertData("board.insertData", dto);
		
		return SUCCESS;
		
	}
	
	public String list() throws Exception {
		
		CommonDAO dao = CommonDAOImpl.getInstance();
		MyUtil myUtil = new MyUtil();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String cp = request.getContextPath();
		
		int numPerPage = 5;
		int totalPage = 0;
		int totalDataCount = 0;
		
		int currentPage = 1;
		
		if(dto.getPageNum()!=null&&!dto.getPageNum().equals(""))
			currentPage = Integer.parseInt(dto.getPageNum());
		
		if(dto.getSearchValue()==null||dto.getSearchValue().equals("")){
			dto.setSearchKey("subject");
			dto.setSearchValue("");
		}
		
		if(request.getMethod().equalsIgnoreCase("GET")){
			dto.setSearchValue(URLDecoder.decode(dto.getSearchValue(),"UTF-8"));
		}
		
		Map<String, Object> hMap = new HashMap<String, Object>();
		
		hMap.put("searchKey",dto.getSearchKey());
		hMap.put("searchValue",dto.getSearchValue());
		
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
			
			sssBoardDTO vo = (sssBoardDTO)it.next();
			listNum = totalDataCount - (start+n-1);
			vo.setListNum(listNum);
			n++;
		}
		

		String param = "";
		String urlArticle = "";
		String urlList = "";
		
		//검색한것이 있다면
		if(!dto.getSearchValue().equals("")){
			
			param += "searchKey="+dto.getSearchKey();
			param += "&searchValue="+URLEncoder.encode(dto.getSearchValue(),"UTF-8");
			
		}
		
		urlList += cp +"/bbs/list.action";
		urlArticle += cp + "/bbs/article.action?pageNum="+currentPage;
		
		if(!param.equals("")){
			urlList += "?" + param;
			urlArticle += "&" + param;
		}
		
		request.setAttribute("lists", lists);
		request.setAttribute("totalDataCount", totalDataCount);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		request.setAttribute("urlArticle", urlArticle);
		
		
		return SUCCESS;
	}
	

	public String article() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		CommonDAO dao = CommonDAOImpl.getInstance();
		
		String searchKey = dto.getSearchKey();
		String searchValue = dto.getSearchValue();
		String pageNum = dto.getPageNum();
		int boardNum = dto.getBoardNum();
		
		
		
		if(searchValue==null||searchValue.equals("")){
			
			searchKey="subject";
			searchValue="";
			
		}
		
		if(request.getMethod().equalsIgnoreCase("GET")){
			searchValue=URLDecoder.decode(searchValue,"UTF-8");
		}
		
		//조회수 증가
		dao.updatetData("board.updateHitCount",boardNum);
		
		//해당 게시글의 정보 가져오기
		dto = (sssBoardDTO)dao.getReadData("board.readData",boardNum);
		
		if(dto==null){
			return "read-error";
		}
		
		int lineSu = dto.getContent().split("\n").length;
		
		dto.setContent(dto.getContent().replace("\n","<br/>"));
		
		//이전글,다음글
		Map<String, Object> hMap = new HashMap<String, Object>();
		
		hMap.put("searchKey",searchKey);
		hMap.put("searchValue",searchValue);
		hMap.put("groupNum", dto.getGroupNum());
		hMap.put("orderNo",dto.getOrderNo());
		
		sssBoardDTO preDTO = (sssBoardDTO)dao.getReadData("board.preReadData",hMap);
		int preBoardNum = 0;
		String preSubject ="";
		if(preDTO!=null){
			preBoardNum = preDTO.getBoardNum();
			preSubject = preDTO.getSubject();
		}
		
		sssBoardDTO nextDTO = (sssBoardDTO)dao.getReadData("board.nextReadData",hMap);
		int nextBoardNum = 0;
		String nextSubject ="";
		if(nextDTO!=null){
			nextBoardNum = nextDTO.getBoardNum();
			nextSubject = nextDTO.getSubject();
		}
		

		String params = "pageNum="+pageNum;
		if(!searchValue.equals("")){
			params += "&searchKey=" + searchKey;
			params += "&searchValue=" + URLEncoder.encode(searchValue,"UTF-8");
		}
		
		
		request.setAttribute("preBoardNum", preBoardNum);
		request.setAttribute("preSubject", preSubject);
		request.setAttribute("nextBoardNum", nextBoardNum);
		request.setAttribute("nextSubject", nextSubject);
		request.setAttribute("params",params);
		request.setAttribute("lineSu",lineSu);
		request.setAttribute("pageNum",pageNum);
		
		return SUCCESS;
		
	}
	
	public String deleted() throws Exception{

		HttpServletRequest request = ServletActionContext.getRequest();
		CommonDAO dao = CommonDAOImpl.getInstance();

		String searchKey = dto.getSearchKey();
		String searchValue = dto.getSearchValue();
		
		dao.delteData("board.deleteData",dto.getBoardNum());
		
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchValue", searchValue);
		
		return SUCCESS;
		
	}

}
