package com.emorphis.cashmanagement.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchClosedGroupDao;
import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RequestScopedPermissions;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BranchClosedGroupService;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.util.MailUtility;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class BranchClosedGroupServiceImpl implements BranchClosedGroupService {

	private static final Logger log = LoggerFactory.getLogger(BranchClosedGroupServiceImpl.class);

	@Autowired
	BranchClosedGroupDao branchClosedGroupDao;

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	BranchManagementDao branchManagementDao;

	@Autowired
	MySession mySession;
	
	@Autowired
	RequestScopedPermissions requestScope;

	@Autowired
	Utility utility;

	@Autowired
	MailUtility mailUtility;

	@Override
	public List<BranchClosedGroup> getBranchClosedBranchList(BranchManagement branchManagement) {
		List<BranchClosedGroup> branchClosedGroupsList = branchClosedGroupDao
				.getBranchClosedBranchList(branchManagement);
		return branchClosedGroupsList;
	}

	/**
	 * @author gourav get list of all already assign branches
	 */
	@Override
	public List<BranchGroup> getBranchClosedBranchList(String groupId) {
		List<BranchGroup> branchGroupsList = branchClosedGroupDao.getGroupBranchList(groupId);
		return branchGroupsList;
	}

	@Override
	public void updateUserRoles(BranchClosedGroup branchClosedGroup) {
		try {
			if (null != branchClosedGroup.getSelectedVA()) {
				System.out.println("Selected array value is : " + branchClosedGroup.getSelectedVA());
				System.out.println("SelectedVa size is : " + branchClosedGroup.getSelectedVA().size());
			}
			if (null != branchClosedGroup.getDeletedVA()) {
				System.out.println("Deleted array value is : " + branchClosedGroup.getDeletedVA());
				System.out.println("Deleted array size is : " + branchClosedGroup.getDeletedVA().size());
			}
			if (null != branchClosedGroup.getInsertedVA()) {
				System.out.println("Inserted Array value is : " + branchClosedGroup.getInsertedVA());
				System.out.println("Inserted Array size is : " + branchClosedGroup.getInsertedVA().size());
			}
			System.out.println("User id in UserRole Association is : " + branchClosedGroup.getParentBranch().getId()
					+ " and username is : " + branchClosedGroup.getParentBranch().getBranchName());

			BranchManagement branchManagement = branchManagementService
					.findById(branchClosedGroup.getParentBranch().getId());
			System.out.println("Branch closed group 1 : " + branchManagement.getBranchClosedGroups().size());
			System.out.println("Branch closed group 2 : " + branchManagement.getBranchClosedGroups2().size());
			Set<BranchClosedGroup> branchClosedGroupsSet = new HashSet<BranchClosedGroup>();

			Iterator it = branchClosedGroup.getSelectedVA().iterator();
			BranchClosedGroup branchClosedGroupObj = null;
			BranchManagement branchManagement2 = null;
			while (it.hasNext()) {
				String branchId = (String) it.next();
				branchManagement2 = new BranchManagement();
				branchClosedGroupObj = new BranchClosedGroup();

				branchManagement2.setId(branchId);
				branchClosedGroupObj.setParentBranch(branchManagement);
				branchClosedGroupObj.setClosedGroupBranch(branchManagement2);
				branchClosedGroupObj.setCreatedTime(new Date());
				branchClosedGroupObj.setModifiedTime(new Date());
				branchClosedGroupObj.setModifiedBy(mySession.getUser().getId());
				branchClosedGroupObj.setCreatedBy(mySession.getUser().getId());
				String uuid = utility.getBranchClosedGroupUUID();
				if (uuid != null) {
					branchClosedGroupObj.setId(uuid);
				}
				branchClosedGroupsSet.add(branchClosedGroupObj);

				System.out.println("closedgroupbranch id for parent branch id : " + branchManagement.getId()
						+ " and name : " + branchManagement.getBranchName() + "   is : " + branchManagement2.getId());
			}
			System.out.println("UserRoleSet size is : " + branchClosedGroupsSet.size());

			branchManagement.setBranchClosedGroups2(branchClosedGroupsSet);
			// userManagementService.saveUserRoles(user);

			Set<BranchClosedGroup> removeUserRoleSet = new HashSet<BranchClosedGroup>();
			if (branchClosedGroup.getDeletedVA().size() > 0) {
				BranchClosedGroup BranchClosedGroupObj2 = null;
				BranchManagement branchManagement3 = null;
				Iterator it1 = branchClosedGroup.getDeletedVA().iterator();
				while (it1.hasNext()) {
					String branchId = it1.next().toString();
					branchManagement3 = new BranchManagement();
					branchManagement3.setId(branchId);
					BranchClosedGroupObj2 = new BranchClosedGroup();
					BranchClosedGroupObj2.setClosedGroupBranch(branchManagement3);
					BranchClosedGroupObj2.setParentBranch(branchManagement);

					removeUserRoleSet.add(BranchClosedGroupObj2);
					System.out.println(
							"closedgroupbranch id for parent branch id: " + branchManagement.getId() + " and name : "
									+ branchManagement.getBranchName() + "   is : " + branchManagement3.getId());
				}
				// user.setUserRoles(removeUserRoleSet);
				System.out.println("RemoveUserRoleSet size is : " + removeUserRoleSet.size());
				deletClosedGroupSetByParentBranch(branchManagement, removeUserRoleSet);
			}

			/**
			 * Now defining cross closed group branches for every branch which
			 * are associated with this branch
			 */
			Set<BranchClosedGroup> branchClosedGroupsSet1 = new HashSet<BranchClosedGroup>();

			Iterator it1 = branchClosedGroup.getSelectedVA().iterator();
			BranchClosedGroup branchClosedGroupObj1 = null;
			BranchManagement branchManagement3 = null;
			while (it1.hasNext()) {
				String branchId = (String) it1.next();

				branchManagement3 = new BranchManagement();
				branchClosedGroupObj1 = new BranchClosedGroup();

				branchManagement3.setId(branchId);
				branchClosedGroupObj1.setParentBranch(branchManagement3);
				branchClosedGroupObj1.setClosedGroupBranch(branchManagement);
				branchClosedGroupObj1.setCreatedTime(new Date());
				branchClosedGroupObj1.setModifiedTime(new Date());
				branchClosedGroupObj1.setModifiedBy(mySession.getUser().getId());
				branchClosedGroupObj1.setCreatedBy(mySession.getUser().getId());
				String uuid = utility.getBranchClosedGroupUUID();
				if (uuid != null) {
					branchClosedGroupObj1.setId(uuid);
				}

				branchClosedGroupsSet1.add(branchClosedGroupObj1);

				System.out.println("closedgroupbranch for cross checking id for parent branch id : "
						+ branchManagement.getId() + " and name : " + branchManagement.getBranchName() + "   is : "
						+ branchManagement3.getId());

			}
			System.out.println("UserRoleSet size is : " + branchClosedGroupsSet1.size());

			/**
			 * Now defining cross closed group branches for internal branch Set
			 */
			{
				// Set<BranchClosedGroup> branchClosedGroupsInternalSet = new
				// HashSet<BranchClosedGroup>();

				Iterator itExternalSet = branchClosedGroup.getSelectedVA().iterator();

				BranchManagement branchManagementExternal = null;
				while (itExternalSet.hasNext()) {
					String branchExternalId = (String) itExternalSet.next();
					branchManagementExternal = new BranchManagement();
					branchManagementExternal.setId(branchExternalId);

					Iterator itInternalSet = branchClosedGroup.getSelectedVA().iterator();
					BranchClosedGroup branchClosedGroupInternalObj = null;
					BranchManagement branchManagementInternal = null;
					while (itInternalSet.hasNext()) {
						String branchId = (String) itInternalSet.next();

						if (!branchExternalId.equals(branchId)) {
							branchManagementInternal = new BranchManagement();
							branchClosedGroupInternalObj = new BranchClosedGroup();

							branchManagementInternal.setId(branchId);

							branchClosedGroupInternalObj.setParentBranch(branchManagementInternal);
							branchClosedGroupInternalObj.setClosedGroupBranch(branchManagementExternal);
							branchClosedGroupInternalObj.setCreatedTime(new Date());
							branchClosedGroupInternalObj.setModifiedTime(new Date());
							branchClosedGroupInternalObj.setModifiedBy(mySession.getUser().getId());
							branchClosedGroupInternalObj.setCreatedBy(mySession.getUser().getId());
							String uuid = utility.getBranchClosedGroupUUID();
							if (uuid != null) {
								branchClosedGroupInternalObj.setId(uuid);
							}

							branchClosedGroupsSet1.add(branchClosedGroupInternalObj);

							System.out.println("closedgroupbranch for cross checking id for parent branch id : "
									+ branchManagement.getId() + " and name : " + branchManagement.getBranchName()
									+ "   is : " + branchManagementInternal.getId());
						}
					}
				}
			}

			branchManagement.setBranchClosedGroups(branchClosedGroupsSet1);

			// saveCrossBranchClosedGroupsByTheParentBranch(branchClosedGroup,
			// branchClosedGroupsSet);

			/**
			 * Also delete the cross branch also automatically...
			 */
			if (removeUserRoleSet.size() > 0) {
				deletCrossClosedGroupSetByParentBranch(branchManagement, removeUserRoleSet);
			} else {
				System.out.println("nothing is there to delete cross closed branch grou set size for delete is ...... "
						+ removeUserRoleSet.size());
			}

			/**
			 * Now sending the notification to the closed branches which are
			 * associated with parent branches
			 */
			List<BranchManagement> cugMailBranchAddressList = new ArrayList<BranchManagement>();
			for (String id : branchClosedGroup.getSelectedVA()) {
				BranchManagement branchManagement4 = branchManagementDao.findById(id);
				cugMailBranchAddressList.add(branchManagement4);

			}
			boolean flag = mailUtility.sendMailToCUGAutomateBranches(cugMailBranchAddressList,
					branchManagement.getBranchName());
			System.out
					.println("Flag value when cug association message reached to the following branches is : " + flag);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deletCrossClosedGroupSetByParentBranch(BranchManagement branchManagement,
			Set<BranchClosedGroup> removeUserRoleSet) {
		branchManagementDao.deletCrossClosedGroupSetByParentBranch(branchManagement, removeUserRoleSet);
	}

	private void saveCrossBranchClosedGroupsByTheParentBranch(BranchClosedGroup branchClosedGroup,
			Set<BranchClosedGroup> branchClosedGroupsSet) {
		branchClosedGroupDao.saveCrossBranchClosedGroupByparentBranch(branchClosedGroup, branchClosedGroupsSet);
	}

	public void deletClosedGroupSetByParentBranch(BranchManagement branchManagement,
			Set<BranchClosedGroup> removeUserRoleSet) throws Exception{
		branchManagementDao.deletClosedGroupSetByParentBranch(branchManagement, removeUserRoleSet);
	}

	@Override
	public void deletClosedBranchesByParentBranch(BranchManagement branchManagement2,
			Set<BranchClosedGroup> branchClosedGroups) throws Exception {
		deletClosedGroupSetByParentBranch(branchManagement2, branchClosedGroups);	
	}
	
	@Override
	public void updateBranchClosedGroupByBranchClusters(BranchManagement branchManagement2,
			List<BranchManagement> branchClusterList, User user) throws Exception {
		BranchClosedGroup branchClosedGroupObj = null;
		try {
			BranchManagement branchManagement = branchManagementService.findByIdWithDelete(branchManagement2.getId());
			Set<BranchClosedGroup> branchClosedGroupsSet = new HashSet<BranchClosedGroup>();
			/*
			 * Set<BranchClosedGroup> existingBranchClosedGroup =
			 * branchManagement.getBranchClosedGroups2(); Set<BranchManagement>
			 * existingBranchManagementClosedGroup = new HashSet<>();
			 * existingBranchManagementClosedGroup =
			 * existingBranchClosedGroup.stream().filter(p->p.
			 * getClosedGroupBranch()).collect(Collectors.toSet());
			 * 
			 * log.info("closedgroupbranch id for parent branch id : " +
			 * branchManagement.getId() + " and name : " +
			 * branchManagement.getBranchName() +
			 * "and existing branch closed group set for the branch : " +
			 * existingBranchClosedGroup.size());
			 * 
			 * Set<BranchManagement> commonBranchRetainList = new
			 * ArrayList<>(branchClusterList); commonBranchRetainList.retainAll(
			 * existingBranchManagementClosedGroup); Set<BranchManagement>
			 * uncommonBranchClusterList = null; uncommonBranchClusterList =
			 * commonBranchRetainList;
			 * 
			 * Set<BranchManagement> newBranchClusterList = new ArrayList<>();
			 * for (BranchManagement branchManagement3 :
			 * uncommonBranchClusterList) { if
			 * (!branchClusterList.contains(branchManagement3)) {
			 * newBranchClusterList.add(branchManagement3); } if() }
			 */
			
//			branchManagement.getBranchClosedGroups().removeAll(branchManagement.getBranchClosedGroups());
			
			log.info("branch closed group set for branch name : " + branchManagement.getBranchName() + " size is : "
					+ branchManagement.getBranchClosedGroups().size());

			/*if (branchManagement.getBranchClosedGroups().size() > 0) {
				log.info("branch closed group size for branch : "+branchManagement.getBranchName()+" is : "+branchManagement.getBranchClosedGroups().size());
				deletClosedGroupSetByParentBranch(branchManagement, branchManagement.getBranchClosedGroups());
				log.info("branch closed group size after delete for branch : "+branchManagement.getBranchName()+" is : "+branchManagement.getBranchClosedGroups().size());
			}*/			
//			Thread.sleep(30000);
			for (BranchManagement branchManagement1 : branchClusterList) {
				branchClosedGroupObj = new BranchClosedGroup();
				branchClosedGroupObj.setParentBranch(branchManagement);
				branchClosedGroupObj.setClosedGroupBranch(branchManagement1);
				branchClosedGroupObj.setCreatedTime(new Date());
				branchClosedGroupObj.setModifiedTime(new Date());
		//		branchClosedGroupObj.setIp(mySession.getIp());
				branchClosedGroupObj.setBranchDistance(branchManagement1.getBranchDistance()); 
				/**
				 * This is because session is not active for different thread.....
				 */
				/*branchClosedGroupObj.setModifiedBy(mySession.getUser().getId());
				branchClosedGroupObj.setCreatedBy(mySession.getUser().getId());*/
				branchClosedGroupObj.setModifiedBy(user.getId());
				branchClosedGroupObj.setCreatedBy(user.getId());
				String uuid = utility.getBranchClosedGroupUUID();
				if (uuid != null) {
					branchClosedGroupObj.setId(uuid);
				}
				branchClosedGroupsSet.add(branchClosedGroupObj);

				log.info("closedgroupbranch id for cluster branch id : " + branchManagement1.getId() + " and name : "
						+ branchManagement1.getBranchName());
			}
			log.info("BranchClusterSet size is : " + branchClosedGroupsSet.size());
			branchManagement.setBranchClosedGroups2(branchClosedGroupsSet);
		} catch (Exception e) {
			log.error("Exception generated in branch closed group while making cluster cluster group for the branch : "
					+ branchManagement2.getBranchName() + " and its id is : " + branchManagement2.getId()+" exception is : "+e);
			e.printStackTrace();
		}
	}
}
