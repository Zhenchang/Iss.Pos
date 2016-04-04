package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.*;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IDiscountsService;
import edu.nus.iss.pos.dao.format.RepoType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscountsService implements IDiscountsService {
    
    private final IUnitOfWork unitOfWork;
    
    public DiscountsService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }
    
    /**
    * getDiscountForTransaction() returns the percentage of discount of the member
    * return 1 if the member is public 
    * and other integer if it is a member
     * @param transaction
     * @return 
     * @throws java.lang.Exception 
    */
    @Override
    public int getDiscountForTransaction(Transaction transaction) throws Exception{
        // TODO Auto-generated method stub
        int periodDiscount = this.getPeriodDiscountForTransaction(transaction);
        int otherDiscount = 0;
        if(transaction.getCustomer() instanceof Member){
             otherDiscount = this.getOtherDiscount(transaction);
        }
        return periodDiscount < otherDiscount ? otherDiscount : periodDiscount;
    }
    
    /**
     * return the percentage of first discount or subsequent discount
     * @param transaction
     * @return
     * @throws Exception 
     */
    private int getOtherDiscount(Transaction transaction) throws Exception {
        Iterable<Discount> discounts = unitOfWork.getRepository(RepoType.Discount).getAll();
        if(this.ifFirstDiscount(transaction)){
            FirstPurchaseDiscount maxDiscount = null;
            for(Discount discount : discounts){
                if(discount instanceof FirstPurchaseDiscount){
                    if(maxDiscount == null){
                        maxDiscount = (FirstPurchaseDiscount) discount;
                    } else if(maxDiscount.getPercentage() < discount.getPercentage()){
                        maxDiscount = (FirstPurchaseDiscount) discount;
                    }
                }
            }
            if(maxDiscount == null)
                return 0;
            return maxDiscount.getPercentage();
        } else {
            SubsequentPurchaseDiscount maxDiscount = null;
            for(Discount discount : discounts){
                if(discount instanceof SubsequentPurchaseDiscount){
                    if(maxDiscount == null){
                        maxDiscount = (SubsequentPurchaseDiscount) discount;
                    } else if(maxDiscount.getPercentage() < discount.getPercentage()){
                        maxDiscount = (SubsequentPurchaseDiscount) discount;
                    }
                }
            }
            if(maxDiscount == null)
                return 0;
            return maxDiscount.getPercentage();
        }
    }
    
    /**
     * Check if it's the first time to discount
     * @param transaction
     * @return 
     * @throws java.lang.Exception 
     */
    private boolean ifFirstDiscount(Transaction transaction) throws Exception{
        Iterable<Transaction> transactions = unitOfWork.getRepository(RepoType.Transaction).getAll();
        for(Transaction item : transactions){
            if(item.getCustomer().getKey().equals(transaction.getCustomer().getKey())){
                return false;
            }
        }
        return true;
    }
    
    /**
     * return the period discount
     * @param transaction
     * @return
     * @throws Exception 
     */
    private int getPeriodDiscountForTransaction(Transaction transaction) throws Exception {
        Customer customer = transaction.getCustomer();
        boolean isMember = customer instanceof Member; 
        PeriodDiscount maxDiscount = null;
        Date today = transaction.getDate();
        Iterable<Discount> discounts = unitOfWork.getRepository(RepoType.Discount).getAll();
        for(Discount discount : discounts){
            if(!(discount instanceof PeriodDiscount))
                continue;
            //check if today is in the discount period
            PeriodDiscount pdiscount = (PeriodDiscount)discount;
            Date newDate = new Date(today.getTime() - pdiscount.getDiscountPeriod() * 24 * 60 * 60 * 1000L);
            if(pdiscount.getStartDate().after(newDate)){
                if(pdiscount.getForMembers()){
                    if(isMember) {
                        if(maxDiscount == null){
                            maxDiscount = pdiscount;
                        } else if (discount.getPercentage() > maxDiscount.getPercentage()){
                            maxDiscount = pdiscount;
                        }
                    }
                } else {
                    if(maxDiscount == null){
                        maxDiscount = pdiscount;
                    }else if(discount.getPercentage() > maxDiscount.getPercentage()){
                        maxDiscount = pdiscount;
                    }
                }
            }
        }
        if(maxDiscount == null)
            return 0;
        return maxDiscount.getPercentage();
    }

    @Override
    public void applyDiscountForTransaction(Transaction transaction) {
            // TODO Auto-generated method stub
            
    }

    @Override
    public void addDiscount(Discount discount) throws Exception {
        unitOfWork.getRepository(RepoType.Discount).add(discount);
    }

    @Override
    public List<Discount> getAllDiscounts() throws Exception{
        List<Discount> discounts = new ArrayList();
        for(Object discount : unitOfWork.getRepository(RepoType.Discount).getAll())
            discounts.add((Discount)discount);
        return discounts;
    }
}
