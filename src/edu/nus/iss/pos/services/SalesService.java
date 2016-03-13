package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.ISalesService;
import edu.nus.iss.pos.dao.format.FileType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import edu.nus.iss.pos.core.dao.IRepository;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalesService implements ISalesService {
	
	private final IUnitOfWork unitOfWork;
    
    public SalesService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }
    
	@Override
	public Transaction beginTransaction(Member member) throws Exception {
            return new Transaction(getNewId(),new Date(),member);
	}

	@Override
	public TransactionDetail addToCart(Transaction transaction, Product product, int quantity) {
            TransactionDetail transactionDetail = new TransactionDetail(transaction, product, quantity);
            transaction.addTransactionDetail(transactionDetail);
            return transactionDetail;
	}

	@Override
	public void checkout(Transaction transaction, boolean useLoyaltyPoints) {
           
	}
        
        private int getNewId() throws Exception{
            int maxId = 0;
            IRepository repository = unitOfWork.getRepository(FileType.Transaction);
            Iterable<Transaction> transaction =  repository.getAll();
            for(Transaction tran : transaction) {
                if(maxId < tran.getId()){
                    maxId = tran.getId();
                }
            }
            return maxId + 1;
        }
}
