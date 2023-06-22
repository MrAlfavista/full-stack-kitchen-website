import React from 'react';
import { Link } from 'react-router-dom';


function Log({user}) {
    if(user.login !== null){
        return <>
        <span>{user.login}  |</span>
        <Link to="/logout">Logout</Link>
      </>;
    } else {
        return <Link to="/login">Log in</Link>;
    }
}

function MyIngredients({user}) {
  if(user.login !== null){
      return <>
      <Link to="/userIngredients">My Ingredients </Link>
    </>;
  } else {
      return;
  }
}


function Navbar({ user }) {
  return (
    <div className="navbar">
      <h2>EXAMPLE LOGO</h2>
      <div className="navbar-links">
        <Link to="/register">Register </Link>
        <Link to="/createDish">Create Dish </Link>
        <Link to="/AddIngredient">Add Ingredient </Link>
        <Link to="/Ingredients">Ingredients </Link>
        <Link to="/Dishes">Dishes </Link>
        <MyIngredients user={user}/>
        <Log user={user} />
      </div>
    </div>
  );
}


export default Navbar;
