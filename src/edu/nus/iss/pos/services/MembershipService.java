package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IMembershipService;

public class MembershipService implements IMembershipService {

	private final IUnitOfWork unitOfWork;
    
    public MembershipService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }
    
	@Override
	public void registerMember(String id, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Member searchMemberByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member searchMemberById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
