package com.ibbs;

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
import com.util.dao.CommonDAOImpl;

public class BoardAction extends ActionSupport implements Preparable,ModelDriven<BoardDTO>{

	private static final long serialVersionUID = 1L;

	private BoardDTO dto;

	public BoardDTO getDto() {
		return dto;
	}
	@Override
	public void prepare() throws Exception {
		dto = new BoardDTO();
	}
	//spring ������� dto �ѱ��
	@Override
	public BoardDTO getModel() {
		return dto;
	}
		
	//spring������� ��ü �̸� ����
	@Resource(name="dao")
	private CommonDAO dao;
	
	@Resource(name="myUtil")
	private MyUtil myUtil;

	
	public String created() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�Է�â
		
		//System.out.println(dto.getMode());
		
		if(dto==null || dto.getMode()==null || dto.getMode().equals("")){
			request.setAttribute("mode", "created");
			return INPUT;
		}

		//�Է¾ȹ��� ���� ���� �Է�(�Խñ��� ��� - ��۸���)
		int maxBoardNum = dao.getIntValue("ibbs.maxBoardNum");
		
		dto.setBoardNum(maxBoardNum+1);
		dto.setIpAddr(request.getRemoteAddr());
		
		//db�� insert
		dao.insertData("ibbs.insertData", dto);
		
		return SUCCESS;
		
	}


	public String list() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		String cp = request.getContextPath();
		
		int numPerPage = 2;
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
		
		totalDataCount = dao.getIntValue("ibbs.dataCount",hMap);
		
		if(totalDataCount!=0)
			totalPage = myUtil.getPageCount(numPerPage, totalDataCount);
		
		if(currentPage>totalPage)
			currentPage = totalPage;
		
		int start = (currentPage-1)*numPerPage + 1;
		int end = currentPage*numPerPage;
		
		hMap.put("start", start);
		hMap.put("end", end);
		
		List<Object> lists = (List<Object>)dao.getListData("ibbs.listData", hMap);
		
		//��ȣ ������
		int listNum,n=0;
		int commentCount=0;
		
		ListIterator<Object> it = lists.listIterator();
		

		while(it.hasNext()){
			
			BoardDTO vo = (BoardDTO)it.next();
			listNum = totalDataCount - (start+n-1);
			vo.setListNum(listNum);
			n++;
			
			commentCount = dao.getIntValue("ibbs.dataCountComment", vo.getBoardNum());
			
			vo.setCommentCount(commentCount);
		
			
		}
		

		String param = "";
		String urlArticle = "";
		String urlList = "";
		
		//�˻��Ѱ��� �ִٸ�
		if(!dto.getSearchValue().equals("")){
			
			param += "searchKey="+dto.getSearchKey();
			param += "&searchValue="+URLEncoder.encode(dto.getSearchValue(),"UTF-8");
			
		}
		
		urlList += cp +"/ibbs/list.action";
		urlArticle += cp + "/ibbs/article.action?pageNum="+currentPage;
		
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
		
		
		//��ȸ�� ����
		dao.updatetData("ibbs.updateHitCount",boardNum);
		
		//�ش� �Խñ��� ���� ��������
		dto = (BoardDTO)dao.getReadData("ibbs.readData",boardNum);
		
		
		if(dto==null){
			return "read-error";
		}
		
		int lineSu = dto.getContent().split("\n").length;
		
		dto.setContent(dto.getContent().replace("\n","<br/>"));
		
		//������,������
		Map<String, Object> hMap = new HashMap<String, Object>();
		
		hMap.put("searchKey",searchKey);
		hMap.put("searchValue",searchValue);
		hMap.put("boardNum",dto.getBoardNum());
		
		BoardDTO preDTO = (BoardDTO)dao.getReadData("ibbs.preReadData",hMap);
		int preBoardNum = 0;
		String preSubject ="";
		
		System.out.println(preDTO);
		
		if(preDTO!=null){
			preBoardNum = preDTO.getBoardNum();
			preSubject = preDTO.getSubject();
		}
		
		BoardDTO nextDTO = (BoardDTO)dao.getReadData("ibbs.nextReadData",hMap);
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
			
			dto = (BoardDTO)dao.getReadData("ibbs.readData",dto.getBoardNum());
			
			if(dto==null)
				return "read-error";
			
			if(!searchValue.equals("")){
				searchValue = URLEncoder.encode(searchValue,"UTF-8");
			}
			
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("mode", "updated");
			request.setAttribute("searchKey", searchKey);
			request.setAttribute("searchValue", searchValue);
			
			return INPUT;
		}
					
		
		dao.updatetData("ibbs.updateData", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchValue", searchValue);
		
		
		return SUCCESS;
		
	}
		
	public String deleted() throws Exception{

		HttpServletRequest request = ServletActionContext.getRequest();

		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String pageNum = request.getParameter("pageNum");
			
		
		dao.delteData("ibbs.deleteData",boardNum);
		request.setAttribute("pageNum", pageNum);
		
		return SUCCESS;
	}	

}
