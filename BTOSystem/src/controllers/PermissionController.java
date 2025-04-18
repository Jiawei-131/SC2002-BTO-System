//package controllers;
//import java.util.*;
//import entities.User;
//import util.Role;
//
//public class PermissionController {
//	private Map<String,Set<String>> rolePermissions;
//	//Permissions check need to be updated based on code changes? Not all permissions are in yet.
//	public PermissionController() {
//		this.rolePermissions=new HashMap<>();
//		
//		Set<String> applicantPermissions = new HashSet<>();
//		applicantPermissions.add("ApplyForProjects");
//		applicantPermissions.add("SubmitEnquiry");
//		applicantPermissions.add("EditEnquiry");
//		applicantPermissions.add("ViewEnquiry");
//		applicantPermissions.add("DeleteEnquiry");
//		rolePermissions.put("Applicant", applicantPermissions);
//		//Done?
//		Set<String> officerPermissions = new HashSet<>();
//		officerPermissions.add("ApplyForProjects");
//		officerPermissions.add("SubmitEnquiry");
//		officerPermissions.add("EditEnquiry");
//		officerPermissions.add("ViewEnquiry");
//		officerPermissions.add("ReplyEnquiry");
//		officerPermissions.add("DeleteEnquiry");
//		officerPermissions.add("GenerateReceipt");
//		rolePermissions.put("Officer", officerPermissions);
//		//Not Done
//		Set<String> managerPermissions = new HashSet<>();
//		managerPermissions.add("ApplyForProjects");
//		managerPermissions.add("ToggleVisibility");
//		managerPermissions.add("CreateBTOProjects");
//		managerPermissions.add("EditBTOProjects");
//		managerPermissions.add("DeleteBTOProjects");
//		managerPermissions.add("ApproveApplicant");
//		managerPermissions.add("RejectApplicant");
//		managerPermissions.add("ViewEnquiryAll");
//		managerPermissions.add("ReplyEnquiry");
//		//Not Done
//		rolePermissions.put("Manager", managerPermissions);
//	}
//	
//	public boolean permissionCheck(User user,String permission) {
//		Role role=user.getRole();
//		
//		Set<String>permissions=rolePermissions.get(role);
//		return permissions != null && permissions.contains(permissions);
//	}
//}
