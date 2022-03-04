package com.creditcard.model;

import java.util.List;

public interface I_CreditcardClassDAO {
	void addCard(CreditcardClassVO creditcardClassVO);
	void deleteCard(Integer crd_no);
	List<CreditcardClassVO> getAll();
	List<CreditcardClassVO> getallByMem_no(Integer mem_no);
}
