package com.iguest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.h;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.util.MyUtil;
import com.util.dao.CommonDAO;

public class GuestAction extends ActionSupport 
implements Preparable, ModelDriven<GuestDTO>{

	private static final long serialVersionUID = 1L;

	private GuestDTO dto;
	
/*
	//guest.jsp�� ajax���� �����͸� �ѱ�⶧���� getter�� ���� �ʿ����
	public GuestDTO getDto() {
		return dto;
	}
*/
	
	
	@Resource(name="dao")
	private CommonDAO dao;
	
	@Resource(name="myUtil")
	private MyUtil myUtil;
	
	
	@Override
	public GuestDTO getModel() {
		return dto;
	}

	@Override
	public void prepare() throws Exception {
		dto = new GuestDTO();
	}
	
	public String created() throws Exception{
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		int maxNum = dao.getIntValue("iguest.numMax");
		
		dto.setNum(maxNum+1);
		dto.setIpAddr(request.getRemoteAddr());
		
		dao.insertData("iguest.insertData", dto);
		
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
		
		totalDataCount = dao.getIntValue("iguest.dataCount");
		
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

		List<Object> lists = (List<Object>)dao.getListData("iguest.listData",hMap);

		System.out.println(lists);
		int listNum,n=0;
		
		Iterator<Object> it = lists.iterator();
		
		while(it.hasNext()){
			
			GuestDTO vo = (GuestDTO)it.next();
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
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		dao.delteData("iguest.deleteData",num);

		return list();
		
	}

}
