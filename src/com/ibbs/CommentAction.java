package com.ibbs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.iguest.GuestDTO;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.util.MyUtil;
import com.util.dao.CommonDAO;

public class CommentAction extends ActionSupport implements Preparable,ModelDriven<CommentDTO>{

	private static final long serialVersionUID = 1L;

	private CommentDTO dto;
	
	@Resource(name="dao")
	private CommonDAO dao;
	
	@Resource(name="myUtil")
	private MyUtil myUtil;
	
	@Override
	public CommentDTO getModel() {
		return dto;
	}

	@Override
	public void prepare() throws Exception {
		dto = new CommentDTO();
	}

	
	public String created() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		int maxNum = dao.getIntValue("ibbs.numMaxComment");
		
		dto.setCommentNum(maxNum+1);
		dto.setIpAddr(request.getRemoteAddr());
		
		System.out.println(dto.getBoardNum());
		
		dao.insertData("ibbs.insertDataComment", dto);
		
		//���� seccess�� ������ list�� �����̷�Ʈ �ϴµ�
		//�׷��� �Ǹ� ��ü�� ���ΰ�ħ �ϴ°� �̱� ������ �ٷ� �Լ� ȣ��
		return list();
		
	}
	
	public String list() throws Exception{
		

		HttpServletRequest request = ServletActionContext.getRequest();
		
		int numPerPage = 5;
		int totalPage = 0;
		int totalDataCount = 0;
		
		String pageNum = request.getParameter("pageNum");//����
		
		int currentPage = 1;
		
		if(pageNum!=null && pageNum!=""){
			currentPage = Integer.parseInt(pageNum);
		}else{
			pageNum = "1";
		}

		
		totalDataCount = dao.getIntValue("ibbs.dataCountComment", Integer.parseInt(request.getParameter("boardNum")));

		
		if(totalDataCount!=0){
			totalPage = myUtil.getPageCount(numPerPage, totalDataCount);
		}
		
		if(currentPage>totalPage)
			currentPage = totalPage;
		
		Map<String, Object> hMap = new HashMap<String, Object>();
		
		int start = (currentPage-1) * numPerPage + 1;
		int end = currentPage*numPerPage;
		
		hMap.put("start", start);
		hMap.put("end", end);
		hMap.put("boardNum", Integer.parseInt(request.getParameter("boardNum")));

		List<Object> lists = (List<Object>)dao.getListData("ibbs.listDataComment",hMap);

		System.out.println(lists);
		int listNum,n=0;
		
		Iterator<Object> it = lists.iterator();
		
		while(it.hasNext()){
			
			CommentDTO vo = (CommentDTO)it.next();
			listNum = totalDataCount - (start+n-1);
			vo.setListNum(listNum);
			
			vo.setContent(vo.getContent().replace("\n", "<br>"));
			n++;
			
		}
		
		//����¡ ó���� �ڹٽ�ũ��Ʈ��
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage);

		request.setAttribute("lists",lists );
		request.setAttribute("pageIndexList",pageIndexList );
		request.setAttribute("totalDataCount",totalDataCount );
		request.setAttribute("pageNum",pageNum );
		
		
		//list�� �����̷�Ʈ�� �ƴϿ��� �׳� success�Ѱ��൵ �ȴ�
		return SUCCESS;
	}
	
	public String deleted() throws Exception{

		HttpServletRequest request = ServletActionContext.getRequest();	
		
		int commentNum = Integer.parseInt(request.getParameter("commentNum"));
		dao.delteData("ibbs.deleteDataComment",commentNum);
		

		request.setAttribute("boardNum",request.getParameter("boardNum"));

		return list();
		
	}
	
}
