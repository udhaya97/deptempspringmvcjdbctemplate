package com.deptempspringmvcjdbcapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.deptempspringmvcjdbcapp.model.Department;
import com.deptempspringmvcjdbcapp.model.Employee;
import com.deptempspringmvcjdbcapp.service.DeptEmpService;

@RestController
public class DeptEmpController {
	
	@Autowired
DeptEmpService deptEmpService;
	
	
	@RequestMapping("/")
	public ModelAndView startCont()
	{
		return new ModelAndView("login");
	}
	
	@PostMapping("/usercheck")
	public ModelAndView loginCheck(HttpServletRequest request,HttpServletResponse response)
	{
		
		String uname=request.getParameter("userName");
		String pass = request.getParameter("password");
		ModelAndView mdv = new ModelAndView("login");
		if(uname.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin"))
		{
			HttpSession sess = request.getSession();
			sess.setAttribute("unam", "admin");
			return new ModelAndView("redirect:/homeserv");
			
			
		}else
		{
			String message = "UserName and Password didnt match";
			request.getSession().setAttribute("message", message);
			return mdv;
		}
		
	}
	
	@RequestMapping("/homeserv")
	public ModelAndView homePage()
	{
		//DeptEmpService dede = new DeptEmpServImpl();
		List<Department> ldeptj = deptEmpService.readAllDeptServ();
		//List<Employee> lemps = dede.readAllEmp();
		//request.setAttribute("empall", lemps);
		ModelAndView mdc = new ModelAndView("home3");
		mdc.addObject("deptlv", ldeptj);
		mdc.addObject("hoser", "hseval");
		
		
		return mdc;
		
	}
	
	@RequestMapping("/home")
	protected ModelAndView home()
{
		
		List<Department> ldept = deptEmpService.readAllDeptServ();
		int deptId =ldept.get(0).getDeptId();
		
		return new ModelAndView("redirect:listEmp?deptId="+deptId);
	
	}
	
	
	
	@RequestMapping("/listEmp")
	public ModelAndView listDepartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("get");
		
		String x = request.getParameter("deptId");
		
		System.out.println("dept id"+x);
		int xt = Integer.parseInt(x);
		System.out.println("int val "+xt);
		List<Employee> ldeptu = deptEmpService.readEmpFromDeptServ(xt);
		Department det =deptEmpService.showDeptServ(xt);
		List<Department> lks = deptEmpService.readAllDeptServ();
		HttpSession sess = request.getSession();
		sess.setAttribute("deIdFromLis", xt);
		sess.setAttribute("emplvaldept", ldeptu);
		sess.setAttribute("lis", lks);
		sess.setAttribute("dval", det);
		sess.setAttribute("val", ldeptu);
		
			System.out.println("values from listemp : ");
			for (Employee employee : ldeptu) {
				System.out.println(employee.getEmpName());
			}
			ModelAndView mdb = new ModelAndView("home3");
			mdb.addObject("val", ldeptu);
			mdb.addObject("lis", lks);
			mdb.addObject("dval", det);
			mdb.addObject("hom", "homep");
			request.setAttribute("countv", sess.getAttribute("couval"));
			//request.setAttribute("mess", "no data available");
			//request.setAttribute("deptnam", arg1);
			
			return mdb;
	}
	
	@RequestMapping("/deleteemployee")
	public ModelAndView deleteEmployee(HttpServletRequest request, HttpServletResponse response) 
{
		
		
		int empId = Integer.parseInt(request.getParameter("empId"));
		int deptempid = Integer.parseInt(request.getParameter("deptEmpId"));
		
		
		
		
		deptEmpService.deleteEmpFromDeptServ(deptempid, empId);
		System.out.println("deleting at del emplo");
		HttpSession sen = request.getSession();
		sen.setAttribute("deleteDone","done");
		//response.sendRedirect("listEmp?deptId="+deptempid);
		return new ModelAndView("redirect:listEmp?deptId="+deptempid);
		}
	
	
	@RequestMapping("/editemployee")
	protected ModelAndView editEmployee(HttpServletRequest request, HttpServletResponse response)
 {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int empId=Integer.parseInt(request.getParameter("empId"));
		System.out.println("employee id at edit employee is "+empId);
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		
		Employee emp = (Employee) deptEmpService.readEmployeeServ(empId);
		Department df = deptEmpService.showDeptServ(emp.getDeptEmpId());
		System.out.println("edit page value"+emp.getEmpName());
		HttpSession sed = request.getSession();
		List<Employee> listFromDept=deptEmpService.readEmpFromDeptServ(deptId);
		sed.setAttribute("empp", emp);
		ModelAndView mcn = new ModelAndView("home3");
		mcn.addObject("mainemps", "checktableedit");
		mcn.addObject("empl", emp);
		mcn.addObject("hom", "homep");
		mcn.addObject("addlin", "anemp");
		mcn.addObject("lis", sed.getAttribute("lisvaldept"));
		mcn.addObject("val", listFromDept);
		mcn.addObject("dfg", df);
		
			return mcn;
		
	}
	
	@RequestMapping("/updateemployee")
	
protected ModelAndView updateEmployee(HttpServletRequest request, HttpServletResponse response) 
{
			// TODO Auto-generated method stub
			
			HttpSession mlk = request.getSession();
			Employee erd = (Employee)mlk.getAttribute("empp");
			//String idv = request.getParameter("empId");
			int empId =erd.getEmpId();
			System.out.println("id val"+empId);
			String empName=request.getParameter("empName");
			System.out.println("employee Name"+empName);
			String dob=request.getParameter("dob");
			System.out.println("dob "+dob);
			String mailId=request.getParameter("mailId");
			System.out.println("mail Id"+mailId);
			String depsample = request.getParameter("deptEmpName");
			List<Department> lsv = deptEmpService.readAllDeptServ();
			
			int studeptid = 0;
			for (Department department : lsv) {
				if(department.getDeptName().equals(depsample))
				{
					studeptid= department.getDeptId();
				}
			}
			
			System.out.println("values update employee "+ studeptid);
			long mob = Long.parseLong(request.getParameter("mobileNo"));
			float sal = Float.parseFloat(request.getParameter("salary"));
			String comName = request.getParameter("companyName");
			
			Employee emp = new Employee();
			emp.setEmpId(empId);
			emp.setEmpName(empName);
			emp.setMailId(mailId);
			emp.setDateOfBirth(dob);
			emp.setDeptEmpId(studeptid);
			emp.setMobileNo(mob);
			emp.setSalary(sal);
			emp.setCompanyName(comName);
			
			System.out.println("Values from update employee"+empId + " "+empName+" "+mailId+ " "+dob + " "+studeptid+" "+mob+ " "+sal + " "+ comName);
			
			System.out.println("values for updating");
			//System.out.println(empId+" "+empName + " "+ mailId+" "+dob+" "+studeptid);
			
			deptEmpService.updateEmpServ(emp);
			HttpSession sea = request.getSession();
			sea.setAttribute("submitDone","done");
			//response.sendRedirect("listEmp?deptId="+studeptid);
			return new ModelAndView("redirect:listEmp?deptId="+studeptid);
		}
	@RequestMapping("/addemployee")
	protected ModelAndView addEmployee(HttpServletRequest request, HttpServletResponse response)
 {
		
		HttpSession sef = request.getSession();
		int deptId = (int) sef.getAttribute("deIdFromLis");
		List<Employee> lsty = deptEmpService.readEmpFromDeptServ(deptId);
	
		List<Department> ldeptval = deptEmpService.readAllDeptServ();
		String stg =request.getParameter("empId");
		ModelAndView mdv = new ModelAndView("home3");
		if(stg==null)
		{
		
		//request.setAttribute("dept",sef.getAttribute("lisvaldept") );
		mdv.addObject("hom", "homep");
		mdv.addObject("valcheck", "regemployee");
		mdv.addObject("lis",ldeptval);
		//request.setAttribute("dval",sef.getAttribute("dval"));
		mdv.addObject("val",lsty);
		return mdv;
		
		
		}
	else
	{
		int x=Integer.parseInt(stg);
		if(x==0)
		{
			mdv.addObject("hom", "homep");
			mdv.addObject("newtab", "ntabl");
			mdv.addObject("val",lsty);
		//return mdv;
		}
		
	}
		return mdv;
	}
	
	@RequestMapping("/saveemployee")
	protected ModelAndView saveEmployee(HttpServletRequest request, HttpServletResponse response) {
		
		
			Employee eml = new Employee();
			
			//int empId = request.getParameter("empId");
			String name = request.getParameter("empName");
			String mailId = request.getParameter("mailId");
			String dob = request.getParameter("dob");
			long mob = Long.parseLong(request.getParameter("mobileNo"));
			float sal = Float.parseFloat(request.getParameter("salary"));
			String comName = request.getParameter("companyName");
			String deptempName =request.getParameter("deptEmpNa");
	List<Department> lsv = deptEmpService.readAllDeptServ();
			
			int studeptid = 0;
			for (Department department : lsv) {
				if(department.getDeptName().equals(deptempName))
				{
					studeptid= department.getDeptId();
				}
			}
			
			System.out.println("dept id"+studeptid);
			
			
			//eml.setEmpId(0);
			eml.setEmpName(name);
			eml.setMailId(mailId);
			eml.setDateOfBirth(dob);
			eml.setDeptEmpId(studeptid);
			eml.setCompanyName(comName);
			eml.setMobileNo(mob);
			eml.setSalary(sal);
			System.out.println("values from save employee"+name);
			deptEmpService.createEmpServ(eml);
			HttpSession sem = request.getSession();
			sem.setAttribute("submitDoneEmp","done");
			
			return new ModelAndView("redirect:listEmp?deptId="+studeptid);
			
		}
@RequestMapping("/editdepartment")
protected ModelAndView editDepartment(HttpServletRequest request, HttpServletResponse response) 
{
	
	int deptId= Integer.parseInt(request.getParameter("depId"));
	HttpSession cvb=request.getSession();
	
	List<Department> ldpl = deptEmpService.readAllDeptServ();
	Department dt =deptEmpService.showDeptServ(deptId);
	ModelAndView mch = new ModelAndView("home3");
	//request.setAttribute("deptvalid", "editdept");
	mch.addObject("deptva",dt);
	mch.addObject("hoser", "hseval");
	mch.addObject("deptlv", ldpl);
	cvb.setAttribute("sdt", deptId);
	return mch;
	
}
@RequestMapping("/updatedept")
protected ModelAndView updateDepartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String name=request.getParameter("deptName");
	String loc = request.getParameter("deptLoc");
	//int dId=Integer.parseInt(request.getParameter("deptId"));
	HttpSession ses = request.getSession();
	int deptId =(int) ses.getAttribute("sdt");
	System.out.println("updating values "+ "id "+deptId+"name"+name+"loc"+loc);
	
	Department dpt = new Department(deptId,name,loc);
	
	
	
	deptEmpService.updateDeptServ(dpt);
	HttpSession sel = request.getSession();
	sel.setAttribute("EditDept","done");
	
	return new ModelAndView("redirect:homeserv");
	
}
@RequestMapping("/regDept")
protected ModelAndView addDepartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession sed = request.getSession();
	
	List<Department> ldepty = deptEmpService.readAllDeptServ();
	ModelAndView mvn = new ModelAndView("home3");
	mvn.addObject("adddept", "regdept");
	mvn.addObject("deptlv", ldepty);
	mvn.addObject("hoser", "hseval");
	return mvn;
}
@RequestMapping("/savedept")
protected ModelAndView saveDept(HttpServletRequest request, HttpServletResponse response) {
	
	String deptName=request.getParameter("deptName");
	String deptLoc=request.getParameter("deptLoc");
	Department dt = new Department();
	
	//dt.setDeptId(0);
	dt.setDeptName(deptName);
	dt.setDeptLoc(deptLoc);
	
	
	deptEmpService.createDeptServ(dt);
	HttpSession sem = request.getSession();
	sem.setAttribute("submitDoneDept","done");
	return new ModelAndView("redirect:homeserv");
	
}

@RequestMapping("/deledept")
protected ModelAndView deleteDept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	int depId = Integer.parseInt(request.getParameter("deptId"));
	
	
	deptEmpService.delteDeptServ(depId);
	
	HttpSession sep = request.getSession();
	sep.setAttribute("deleteDoneDept","done");
	return new ModelAndView("redirect:homeserv");
	
}
@RequestMapping("/logout")
protected ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
	
	HttpSession sess = request.getSession();
	sess.removeAttribute("unam");
	sess.invalidate();
	return new ModelAndView("login");
}

}
