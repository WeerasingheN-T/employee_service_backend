import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom'; 

class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {}
    }

    handleLogout = () => {
        localStorage.removeItem('token'); 
        this.props.history.push('/');
    };
    

    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                        <div>
                            <a href="" className="navbar-brand">
                                <img src='/images/empLog.png' alt="Logo" style={{ height: "50px" }} />
                            </a>
                        </div>
                        
                        {localStorage.getItem('token') ? (
                            <div className="collapse navbar-collapse">
                            <ul className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <button className="btn btn-primary" onClick={this.handleLogout}>Log Out</button>
                                </li>
                            </ul>
                        </div>
                        ):(
                        <div className="collapse navbar-collapse">
                            <ul className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link className="nav-link" to="/">Sign In</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/signup">Sign Up</Link>
                                </li>
                            </ul>
                        </div>
                        ) }
                        
                    </nav>
                </header>
            </div>
        );
    }
}

export default withRouter(HeaderComponent);