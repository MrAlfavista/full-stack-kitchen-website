import React, { useEffect, useState } from 'react';
import axios from 'axios';

const UserIngredients = () => {
  const [userIngredients, setUserIngredients] = useState([]);

  useEffect(() => {
    const fetchUserIngredients = async () => {
      const userId = localStorage.getItem('userId');
      const response = await axios.get(`http://localhost:8080/getUserIngredients?userId=${userId}`);
      setUserIngredients(response.data);
    };

    fetchUserIngredients();
  }, []);

  return (
    <div>
      <h1>User's Ingredients</h1>
      {userIngredients.map((ingredient, index) => (
        <div key={index}>
          <p><img src={ingredient.ingredient.imageUrl} alt={ingredient.name} style={{width: "50px", height: "auto"}}/> {ingredient.ingredient.name}, {ingredient.count} {ingredient.ingredient.measure}</p>
        </div>
      ))}
    </div>
  );
}

export default UserIngredients;
