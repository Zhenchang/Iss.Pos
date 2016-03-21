package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.*;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IDiscountsService;
import edu.nus.iss.pos.dao.format.FileType;
import java.util.Date;

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
    */
    @Override
    public float getDiscountForTransaction(Transaction transaction) throws Exception{
        // TODO Auto-generated method stub
        float periodDiscount = this.getPeriodDiscountForTransaction(transaction);
        float otherDiscount = this.getOtherDiscount(transaction);
        return periodDiscount > otherDiscount ? periodDiscount : otherDiscount;
    }
    
    /**
     * return the percentage of first discount or subsequent discount
     * @param transaction
     * @return
     * @throws Exception 
     */
    private float getOtherDiscount(Transaction transaction) throws Exception {
        Iterable<Discount> discounts = unitOfWork.getRepository(FileType.Discount).getAll();
        FirstPurchaseDiscount maxDiscount = null;
        if(this.ifFirstDiscount(transaction)){
            for(Discount discount : discounts){
                if(discount instanceof FirstPurchaseDiscount){
                    if(maxDiscount == null){
                        maxDiscount = (FirstPurchaseDiscount) discount;
                    } else if(maxDiscount.getPercentage() < discount.getPercentage()){
                        maxDiscount = (FirstPurchaseDiscount) discount;
                    }
                }
            }
        } else {
            for(Discount discount : discounts){
                if(discount instanceof SubsequentPurchaseDiscount){
                    if(maxDiscount == null){
                        maxDiscount = (FirstPurchaseDiscount) discount;
                    } else if(maxDiscount.getPercentage() < discount.getPercentage()){
                        maxDiscount = (FirstPurchaseDiscount) discount;
                    }
                }
            }
        }
        
        
        return maxDiscount.getPercentage();
    }
    
    /**
     * Check if it's the first time to discount
     * @param transaction
     * @return 
     */
    private boolean ifFirstDiscount(Transaction transaction) throws Exception{
        Iterable<Transaction> transactions = unitOfWork.getRepository(FileType.Transaction).getAll();
        for(Transaction item : transactions){
            if(item.getCustomer().getKey().equals(transaction.getCustomer().getKey())){
                return true;
            }
        }
        return false;
    }
    
    /**
     * return the period discount
     * @param transaction
     * @return
     * @throws Exception 
     */
    private float getPeriodDiscountForTransaction(Transaction transaction) throws Exception {
        Customer customer = transaction.getCustomer();
        boolean isMember = customer instanceof Member; 
        PeriodDiscount maxDiscount = null;
        Date today = new Date();
        Iterable<PeriodDiscount> discounts = unitOfWork.getRepository(FileType.Discount).getAll();
        for(PeriodDiscount discount : discounts){
            //check if today is in the discount period
            if(discount.getStartDate().before(new Date(today.getTime() + discount.getDiscountPeriod() * 24 * 60 * 60 * 1000))){
                if(discount.getForMembers() && isMember){
                    if(maxDiscount == null){
                        maxDiscount = discount;
                    }else if(discount.getPercentage() > maxDiscount.getPercentage()){
                        maxDiscount = discount;
                    }                   
                }
                else if(!discount.getForMembers()){
                    if(maxDiscount == null){
                        maxDiscount = discount;
                    } else if (discount.getPercentage() > maxDiscount.getPercentage()){
                        maxDiscount = discount;
                    }
                }
            }
        }
        return maxDiscount.getPercentage();
    }

    @Override
    public void applyDiscountForTransaction(Transaction transaction) {
            // TODO Auto-generated method stub
            
    }

    @Override
    public void addDiscount(Discount discount) throws Exception {
        unitOfWork.getRepository(FileType.Discount).add(discount);
    }
}
