package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.ISalesService;

public class SalesService implements ISalesService {
	
	private final IUnitOfWork unitOfWork;
    
    public SalesService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }
    
	@Override
	public Transaction beginTransaction(Member member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionDetail addToCart(Transaction transaction, Product product, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkout(Transaction transaction, boolean useLoyaltyPoints) {
		// TODO Auto-generated method stub

	}

}
