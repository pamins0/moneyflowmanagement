package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchGroupDao;
import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.BranchGroupService;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.util.MailUtility;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class BranchGroupServiceImpl implements BranchGroupService {

	@Autowired
	BranchGroupDao branchGroupDao;

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	BranchManagementDao branchManagementDao;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	MailUtility mailUtility;

	/**
	 * @author gourav get list of all already assign branches
	 */
	@Override
	public List<BranchGroup> getBranchGroupList(BranchGroup branchGroup) {
		List<BranchGroup> branchGroupsList = branchGroupDao.getGroupBranchList(branchGroup);
		return branchGroupsList;
	}

	@Override
	@Transactional
	public void updateBranchGroup(BranchGroup branchGroup) {
		try {

			/*
			 * if (null != branchGroup.getSelectedVA()) {
			 * System.out.println("Selected array value is : " +
			 * branchGroup.getSelectedVA());
			 * System.out.println("SelectedVa size is : " +
			 * branchGroup.getSelectedVA().size()); } if (null !=
			 * branchGroup.getDeletedVA()) {
			 * System.out.println("Deleted array value is : " +
			 * branchGroup.getDeletedVA());
			 * System.out.println("Deleted array size is : " +
			 * branchGroup.getDeletedVA().size()); } if (null !=
			 * branchGroup.getInsertedVA()) {
			 * System.out.println("Inserted Array value is : " +
			 * branchGroup.getInsertedVA());
			 * System.out.println("Inserted Array size is : " +
			 * branchGroup.getInsertedVA().size()); }
			 */
			System.out.println("Group Id is : " + branchGroup.getGroupId());
			if (branchGroup.getGroupId() == null || branchGroup.getGroupId().trim().length() == 0) {
				branchGroup.setGroupId(UUID.randomUUID().toString());
				System.out.println("Group Id is : " + branchGroup.getGroupId());
			}

			for (String branchId : branchGroup.getSelectedVA()) {
				BranchGroup branchGroupObj = new BranchGroup();
				BranchManagement branchManagement = new BranchManagement();
				branchManagement.setId(branchId);

				branchGroupObj.setGroupId(branchGroup.getGroupId());
				branchGroupObj.setGroupName(branchGroup.getGroupName());
				branchGroupObj.setBranchManagement(branchManagement);
				branchGroupObj.setCreatedTime(new Date());
				branchGroupObj.setModifiedTime(new Date());
				branchGroupObj.setModifiedBy(mySession.getUser().getId());
				branchGroupObj.setCreatedBy(mySession.getUser().getId());
				String uuid = utility.getBranchGroupUUID();
				if (uuid != null) {
					branchGroupObj.setId(uuid);
				}
				branchGroupDao.saveBranchGroup(branchGroupObj);
			}

			if (branchGroup.getDeletedVA() != null && branchGroup.getDeletedVA().size() > 0) {
				branchGroupDao.deleteBranchGroup(branchGroup.getDeletedVA());
			}

			// update group name
			List<BranchGroup> branchGroups = branchGroupDao.getGroupBranchList(branchGroup);
			for (BranchGroup branchGroupName : branchGroups) {
				branchGroupName.setGroupName(branchGroup.getGroupName());
				branchGroupDao.saveBranchGroup(branchGroupName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateBranchColsedGroup(BranchGroup branchGroup) {
		try {

			/*if (null != branchGroup.getSelectedVA()) {
				System.out.println("Selected array value is : " + branchGroup.getSelectedVA());
				System.out.println("SelectedVa size is : " + branchGroup.getSelectedVA().size());
			}
			if (null != branchGroup.getDeletedVA()) {
				System.out.println("Deleted array value is : " + branchGroup.getDeletedVA());
				System.out.println("Deleted array size is : " + branchGroup.getDeletedVA().size());
			}
			if (null != branchGroup.getInsertedVA()) {
				System.out.println("Inserted Array value is : " + branchGroup.getInsertedVA());
				System.out.println("Inserted Array size is : " + branchGroup.getInsertedVA().size());
			}*/

			System.out.println("Group Id is : " + branchGroup.getGroupId());
			if (branchGroup.getGroupId() == null || branchGroup.getGroupId().trim().length() == 0) {
				branchGroup.setGroupId(UUID.randomUUID().toString());
				System.out.println("Group Id is : " + branchGroup.getGroupId());
			}

			List<String> insertedList = branchGroup.getInsertedVA();
			List<String> selectedList = branchGroup.getSelectedVA();
			List<String> deletedList = branchGroup.getDeletedVA();

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

			System.out.println("Group Id is : " + branchGroup.getGroupId());
			
			if (branchGroup.getGroupId() == null || branchGroup.getGroupId().trim().length() == 0) {
				branchGroup.setGroupId(UUID.randomUUID().toString());
				System.out.println("Group Id is : " + branchGroup.getGroupId());
			}

			for (String branchId : selectedList) {
				BranchGroup branchGroupObj = new BranchGroup();
				BranchManagement branchManagement = new BranchManagement();
				branchManagement.setId(branchId);

				branchGroupObj.setGroupId(branchGroup.getGroupId());
				branchGroupObj.setGroupName(branchGroup.getGroupName());
				branchGroupObj.setBranchManagement(branchManagement);
				branchGroupObj.setCreatedTime(new Date());
				branchGroupObj.setModifiedTime(new Date());
				branchGroupObj.setModifiedBy(mySession.getUser().getId());
				branchGroupObj.setCreatedBy(mySession.getUser().getId());
				String uuid = utility.getBranchGroupUUID();
				if (uuid != null) {
					branchGroupObj.setId(uuid);
				}
				branchGroupDao.saveBranchGroup(branchGroupObj);
			}

			if (deletedList != null && deletedList.size() > 0) {
				branchGroupDao.deleteBranchGroup(deletedList);
			}

			// update group name
			List<BranchGroup> branchGroups = branchGroupDao.getGroupBranchList(branchGroup);
			for (BranchGroup branchGroupName : branchGroups) {
				branchGroupName.setGroupName(branchGroup.getGroupName());
				branchGroupDao.saveBranchGroup(branchGroupName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<BranchManagement> getBranchManagementListByGroupId(String groupId) {
		List<BranchManagement> branchManagementList = branchGroupDao.getBranchManagementListByGroupId(groupId);
		return branchManagementList;
	}

	@Override
	public Set<String> getCCGroupList() {

		return branchGroupDao.getCCGroupList();
	}

	@Override
	public boolean deleteByGroupId(String groupId) {
		branchGroupDao.deleteByGroupId(groupId);
		return true;
	}

}
