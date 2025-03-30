import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import UserService from '../services/UserService'

class SignUp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            empId:'',
            email: '',
            password: '',
            confirmPassword: ''
        };
        this.state = {
            message: ''
        }
    }

    handleChange = (e) => {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSubmit = (e) => {
        e.preventDefault();
        if (this.state.password !== this.state.confirmPassword) {
            alert('Passwords do not match!');
            return;
        }
        
        let user = {empId: this.state.empId, email: this.state.email, password: this.state.password};
        UserService.registerUser(user).then((res) => {
            this.setState({ message: res.data},() => {
                alert(`${this.state.message}`);
                this.props.history.push("/");

            });
        }).catch((error)=>{
            if (error.response && error.response.data) {
                alert(error.response.data); // Show error message from backend
            } else {
                alert("An error occurred. Please try again.");
            }
        })
    }

    render() {
        return (
            <div className="container mt-5">
                <div className = "card col-md-6 offset-md-3 offset-md-3">
                <h2 className="text-center">Sign Up</h2>
                <form onSubmit={this.handleSubmit}>
                <div className="form-group">
                        <label htmlFor="empId">Employee ID</label>
                        <input
                            type="empId"
                            className="form-control"
                            id="empId"
                            name="empId"
                            value={this.state.empId}
                            onChange={this.handleChange}
                            placeholder="Enter employee ID"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email address</label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            name="email"
                            value={this.state.email}
                            onChange={this.handleChange}
                            placeholder="Enter email"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            name="password"
                            value={this.state.password}
                            onChange={this.handleChange}
                            placeholder="Enter password"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="confirmPassword">Confirm Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="confirmPassword"
                            name="confirmPassword"
                            value={this.state.confirmPassword}
                            onChange={this.handleChange}
                            placeholder="Confirm password"
                            required
                        />
                    </div>
                    <div className="text-center mt-3">
                        <button type="submit" className="btn btn-primary">Sign Up</button>
                    </div>
                </form>
                <p className="text-center mt-3">
                    Already have an account? <Link to="/signin">Sign In</Link>
                </p>
                </div>
            </div>
        );
    }
}

export default SignUp;
