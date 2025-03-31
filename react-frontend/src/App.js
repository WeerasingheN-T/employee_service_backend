import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListEmployeeComponent from './components/ListEmployeeComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import CreateEmployeeComponent from './components/CreateEmployeeComponent';
import ViewEmployeeComponent from './components/ViewEmployeeComponent';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import PrivateRoute from './securoty/PrivateRoute';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {SignIn}/>
                          <Route path = "/signup" component = {SignUp} />
                          <PrivateRoute path = "/employees" component = {ListEmployeeComponent} />
                          <PrivateRoute path = "/employee/:id" component = {CreateEmployeeComponent} />
                          <PrivateRoute path = "/employee-view/:id" component = {ViewEmployeeComponent} />
                          
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;
