package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IDiscountsService;

public class DiscountsService implements IDiscountsService {


	private final IUnitOfWork unitOfWork;
    
    public DiscountsService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }
    
	@Override
	public int getDiscountForTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void applyDiscountForTransaction(Transaction transaction) {
		// TODO Auto-generated method stub

	}

}
