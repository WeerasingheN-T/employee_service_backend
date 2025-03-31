import axios from 'axios';

const EMPLOYEE_API_BASE_URL = "http://localhost:8080/api/v1/employees";

class EmployeeService {

    getAuthHeaders() {

        let token="";
        if(localStorage.getItem('token')){
           token = localStorage.getItem('token'); 
        }
        return {
            headers: {
                Authorization: `Bearer ${token}`
            }
        };
    }

    getEmployees(){
        return axios.get(EMPLOYEE_API_BASE_URL, this.getAuthHeaders());
    }

    createEmployee(employee){
        return axios.post(EMPLOYEE_API_BASE_URL, employee, this.getAuthHeaders());
    }

    getEmployeeById(employeeId){
        return axios.get(`${EMPLOYEE_API_BASE_URL}/${employeeId}`);
    }

    updateEmployee(employee, employeeId){
        return axios.put(`${EMPLOYEE_API_BASE_URL}/${employeeId}`, employee, this.getAuthHeaders());
    }

    deleteEmployee(employeeId){
        return axios.delete(`${EMPLOYEE_API_BASE_URL}/${employeeId}`, this.getAuthHeaders());
    }
}

export default new EmployeeService()