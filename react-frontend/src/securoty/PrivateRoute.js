import React, { Component } from "react";
import { Route, Redirect} from "react-router-dom";

const isAuthenticated =()=> {
    return localStorage.getItem('token') !== null;
};

const PrivateRoute = ({ component: Component, ...rest }) => {

    return(
        <Route 
           {...rest}
           render={(props)=>
            isAuthenticated() ? (
                <Component {...props} />
            ) : (
                <Redirect to="/" />
            )
           }
        />
    );
};

export default PrivateRoute;