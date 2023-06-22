import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Register from './Register';
import CreateDish from './CreateDish';
import AddIngredient from './AddIngredient';
import Login from './Login';
import UserIngredients from './UserIngredients';
import Logout from './Logout';
import Navbar from './Navbar';
import Ingredients from './Ingredients';
import Dishes from './Dishes';
import DishDetails from './DishDetails';
import AddTrademark from './AddTrademark';
import IngredientDetails from './IngredientDetails';
import './App.css';

function App() {

  const user = { login: localStorage.getItem('user') };
  return (
    <Router>
      <Navbar user = {user} />
      <Switch>
        <Route path="/register" component={Register} />
        <Route path="/createDish" component={CreateDish} />
        <Route path="/AddIngredient" component={AddIngredient} />
        <Route path="/Ingredients" component={Ingredients} />
        <Route path="/Dishes" component={Dishes} />
        <Route path="/login" component={Login} />
        <Route path="/logout" component={Logout} />
        <Route path="/userIngredients" component={UserIngredients} />
        <Route path="/dish/:id" component={DishDetails} />
        <Route path="/ingredient/:id" component={IngredientDetails} />
        <Route path="/addTrademark/:id" component={AddTrademark} />
      </Switch>
    </Router>
  );
}

export default App;



