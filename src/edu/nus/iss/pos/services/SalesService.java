package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.ISalesService;
import edu.nus.iss.pos.dao.format.RepoType;
import java.util.Date;
import edu.nus.iss.pos.core.dao.IRepository;

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
    public void checkout(Transaction transaction,float discount, boolean useLoyaltyPoints) throws Exception {
        boolean isMember = transaction.getCustomer() instanceof Member;
        float price = getPriceAfterDiscount(transaction, discount);
        if(isMember && useLoyaltyPoints){
            Member member = (Member) transaction.getCustomer();
            member.redeemPoints(price, true);
       }
       unitOfWork.getRepository(RepoType.Transaction).update(transaction.getKey(), transaction);
    }
        
    private int getNewId() throws Exception{
        int maxId = 0;
        IRepository repository = unitOfWork.getRepository(RepoType.Transaction);
        Iterable<Transaction> transaction =  repository.getAll();
        for(Transaction tran : transaction) {
            if(maxId < tran.getId()){
                maxId = tran.getId();
            }
        }
        return maxId + 1;
    }
    
    public float getPriceAfterDiscount(Transaction transaction, float discount) throws Exception {
       float price = transaction.getTotalWithoutDiscount();
       price -=  price * (discount/100);
       return price;
    }

    @Override
    public float getFinalPrice(Transaction transaction, float discount, boolean useLoyaltyPoints) throws Exception {
       boolean isMember = transaction.getCustomer() instanceof Member;
       float price = getPriceAfterDiscount(transaction, discount);
       if(isMember && useLoyaltyPoints){
            Member member = (Member) transaction.getCustomer();
            price = member.redeemPoints(price, false);
       }
       return price;
    }
}
