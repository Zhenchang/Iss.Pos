package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.dao.IRepository;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IMembershipService;
import edu.nus.iss.pos.dao.format.RepoType;

public class MembershipService implements IMembershipService {

	private final IUnitOfWork unitOfWork;
    
    public MembershipService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }
    
	@Override
	public Member registerMember(String id, String name) throws Exception {
		
		Member m = new Member(id,name);
		unitOfWork.add(m);
		return m;
	}

	@Override
	public Member searchMemberByName(String name) throws Exception {
		
		IRepository repository = unitOfWork.getRepository(RepoType.Member);
		Iterable<Member> members =  repository.getAll();
		for(Member m : members) {
			if(m.getName().equals(name)){
				return m;
			}
		}
		return null;
	}

	@Override
	public Member searchMemberById(String id) throws Exception {
		
		IRepository repository = unitOfWork.getRepository(RepoType.Member);
		Member member = (Member) repository.getByKey(id);
		return member;
	}

}
