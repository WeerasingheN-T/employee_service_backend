import axios from 'axios';

const USER_API_BASE_URL = "http://localhost:8080/api/auth";

class UserService {

    registerUser(user){
        return axios.post(USER_API_BASE_URL + '/register',user);
    }

    loginUser(user){
        return axios.post(USER_API_BASE_URL + '/login',user);
    }
}

export default new UserService()