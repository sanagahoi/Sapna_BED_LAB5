package com.glearning.ems.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.glearning.ems.entity.Employee;
import com.glearning.ems.service.EmployeeService;

@Controller
public class EmployeeController {

	public EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// get the list at home page

	@GetMapping("/employees")
	public String listEmployees(Model model) {

		List<Employee> employees = employeeService.listEmployees();

		model.addAttribute("employees", employees);
		return "employees";

	}

	// add new employee
	@GetMapping("/employees/new")
	public String addEmployeeButtonClicked(Model model) {

		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new-employee";

	}

	@PostMapping("/employees")
	public String submitButtonClickedForAddEmployee(
			@ModelAttribute("employee") Employee employee) {

		employeeService.saveEmployee(employee);
		return "redirect:/employees";
	}

	// update employee
	@GetMapping("/employees/edit/{id}")
	public String updateEmployeeButtonclicked(@PathVariable Long id, Model model) {

		Employee selectedEmp = employeeService.getEmployeeById(id);

		model.addAttribute("employee", selectedEmp);

		return "edit-employee";
	}

	@PostMapping("/employees/{id}")
	public String submitButtonForUpdateClicked(
			@PathVariable Long id, @ModelAttribute("employee") Employee employee) {

		Employee existingEmp = employeeService.getEmployeeById(id);

		existingEmp.setFirstName(employee.getFirstName());
		existingEmp.setLastName(employee.getLastName());
		existingEmp.setEmail(employee.getEmail());

		employeeService.updateEmployee(employee);

		return "redirect:/employees";
	}

	// delete employee

	@GetMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable Long id) {

		employeeService.deleteById(id);

		return "redirect:/employees";
	}

}
