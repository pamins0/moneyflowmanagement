package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchCorrespondentDao;
import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.model.BranchCorrespondentGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.BranchCorrespondentService;
import com.emorphis.cashmanagement.util.MailUtility;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class BranchCorrespondentServiceImpl implements BranchCorrespondentService {

	@Autowired
	BranchCorrespondentDao branchCorrespondentDao;
	
	@Autowired
	BranchManagementDao branchManagementDao;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	MailUtility mailUtility;
	
	@Override
	public void updateBranchCorrespondentGroup(BranchCorrespondentGroup branchCorrespondentGroup) {
		try{
			System.out.println("Branch parent Id is : " + branchCorrespondentGroup.getParentCorrespondentBranch().getBranchName());
			
			List<String> insertedList = branchCorrespondentGroup.getInsertedVA();
			List<String> selectedList = branchCorrespondentGroup.getSelectedVA();
			List<String> deletedList = branchCorrespondentGroup.getDeletedVA();

			for (String inserted : insertedList) {
				if (selectedList.contains(inserted)) {
					selectedList.remove(inserted);
				} else {
					deletedList.add(inserted); 
				}
			}

			if (null != selectedList) {
				System.out.println("Selected array value is : " + selectedList);
				System.out.println("SelectedVa size is : " + selectedList.size());
			}
			if (null != deletedList) {
				System.out.println("Deleted array value is : " + deletedList);
				System.out.println("Deleted array size is : " + deletedList.size());
			}
			/*if (null != insertedList) {
				System.out.println("Inserted Array value is : " + insertedList);
				System.out.println("Inserted Array size is : " + insertedList.size());
			}*/

			System.out.println("Branch parent after Id is : " + branchCorrespondentGroup.getParentCorrespondentBranch().getBranchName());
			
			/*if (branchGroup.getGroupId() == null || branchGroup.getGroupId().trim().length() == 0) {
				branchGroup.setGroupId(UUID.randomUUID().toString());
				System.out.println("Group Id is : " + branchGroup.getGroupId());
			}*/

			for (String branchId : selectedList) {
				BranchCorrespondentGroup branchCorrespondentGroup2 = new BranchCorrespondentGroup();
				BranchManagement branchManagement = new BranchManagement();
				branchManagement.setId(branchId);			
				
				branchCorrespondentGroup2.setParentCorrespondentBranch(branchCorrespondentGroup.getParentCorrespondentBranch());
				branchCorrespondentGroup2.setAssociateCorrespondentBranch(branchManagement);
				branchCorrespondentGroup2.setCreatedTime(new Date());
				branchCorrespondentGroup2.setModifiedTime(new Date());
				branchCorrespondentGroup2.setModifiedBy(mySession.getUser().getId());
				branchCorrespondentGroup2.setCreatedBy(mySession.getUser().getId());
				
				String uuid = utility.getBranchGroupUUID();
				if (uuid != null) {
					branchCorrespondentGroup2.setId(uuid);					
				}
				branchCorrespondentDao.saveBranchCorrespondentGroup(branchCorrespondentGroup2); 
			}

			if (deletedList != null && deletedList.size() > 0) {
				branchCorrespondentDao.deleteBranchCorrespondentGroup(deletedList,branchCorrespondentGroup);
			}

			// update group name
			/*List<BranchGroup> branchGroups = branchGroupDao.getGroupBranchList(branchGroup);
			for (BranchGroup branchGroupName : branchGroups) {
				branchGroupName.setGroupName(branchGroup.getGroupName());
				branchGroupDao.saveBranchGroup(branchGroupName);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
