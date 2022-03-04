package com.creditcard.model;

import java.util.List;

public class CreditcardService {
	
	private I_CreditcardClassDAO dao;
	public CreditcardService() {
		dao = new CreditcardClassDAO();
	}
	
	public CreditcardClassVO addCard(Integer mem_no,String crd_name,String crd_num,
			String crd_expiry,String crd_security_code,String crd_barcode){
		
		CreditcardClassVO creditcardVO = new CreditcardClassVO();
		
		creditcardVO.setMem_no(mem_no);
		creditcardVO.setCrd_name(crd_name);
		creditcardVO.setCrd_num(crd_num);
		creditcardVO.setCrd_expiry(crd_expiry);
		creditcardVO.setCrd_security_code(crd_security_code);
		creditcardVO.setCrd_barcode(crd_barcode);
		
		dao.addCard(creditcardVO);
		
		return creditcardVO;
	}
	
	public void deleteCard(Integer crd_no) {
		dao.deleteCard(crd_no);
	}
	
	public  List<CreditcardClassVO> getallByMem_no(Integer mem_no){
		return dao.getallByMem_no(mem_no);
	}
	
	public List<CreditcardClassVO> getAll(){
		return dao.getAll();
	}

}
