package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Customer;
import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.ISalesService;
import edu.nus.iss.pos.dao.format.RepoType;
import java.util.Date;
import edu.nus.iss.pos.core.dao.IRepository;
import java.util.ArrayList;
import java.util.List;

public class SalesService implements ISalesService {

    private final IUnitOfWork unitOfWork;

    public SalesService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Transaction beginTransaction(Customer customer) throws Exception {
        return new Transaction(getNewId(),new Date(),customer);
    }

    @Override
    public TransactionDetail addToCart(Transaction transaction, Product product, int quantity) {
        TransactionDetail transactionDetail = new TransactionDetail(transaction, product, quantity);
        transaction.addTransactionDetail(transactionDetail);
        return transactionDetail;
    }

    @Override
    public void checkout(Transaction transaction,int discount, int usePoints) throws Exception {
        boolean isMember = transaction.getCustomer() instanceof Member;
        float price = getPriceAfterDiscount(transaction, discount);
        if(isMember){
            Member member = (Member) transaction.getCustomer();
            if(usePoints > 0){
                price = member.redeemPoints(price, usePoints, true);
            }
            member.addLoyaltyPoints(price);
            unitOfWork.getRepository(RepoType.Member).update(member.getKey(), member);
        }
        IRepository<Product> productRepo = unitOfWork.getRepository(RepoType.Product);
        for(TransactionDetail d : transaction.getTransactionDetails()){
            d.getProduct().setQuantity(d.getProduct().getQuantity() - d.getQuantityPurchased());
            productRepo.update(d.getProduct().getKey(), d.getProduct());
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
    
    public float getPriceAfterDiscount(Transaction transaction, int discount) throws Exception {
       float price = transaction.getTotalWithoutDiscount();
       price -=  price * (discount/100.0);
       return price;
    }

    @Override
    public float getFinalPrice(Transaction transaction, int discount, int usePoints) throws Exception {
       boolean isMember = transaction.getCustomer() instanceof Member;
       float price = getPriceAfterDiscount(transaction, discount);
       if(isMember && usePoints > 0){
            Member member = (Member) transaction.getCustomer();
            price = member.redeemPoints(price, usePoints, false);
       }
       return price;
    }

    @Override
    public List<Transaction> getTransactions(Date startDate, Date endDate) throws Exception {
        Iterable<Transaction> transactions = unitOfWork.getRepository(RepoType.Transaction).getAll();
        List<Transaction> transactionsInPeriod = new ArrayList();
        for(Transaction trans : transactions) {
            if(trans.getDate().after(startDate) && trans.getDate().before(endDate))
                transactionsInPeriod.add(trans);
        }
        return transactionsInPeriod;
    }
}
