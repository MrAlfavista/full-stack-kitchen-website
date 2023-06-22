import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

function Dishes() {
  const [dishes, setDishes] = useState([]);
  const [search, setSearch] = useState('');
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    const fetchDishes = async () => {
      const response = await axios.get('http://localhost:8080/findAllDishesByNameContaining?name=' + search);
      const dishData = response.data;

      for (let dish of dishData) {
        const canMakeResponse = await axios.get(`http://localhost:8080/canMake?userId=${userId}&dishId=${dish.id}`);
        dish.canMake = canMakeResponse.data;
      }

      setDishes(dishData);
    };
    
    fetchDishes();
  }, [search, userId]);

  return (
    <div>
      <input placeholder="Search" onChange={e => setSearch(e.target.value)} />
      <table>
        <thead>
          <tr>
            <th>Image</th>
            <th>Dish</th>
            <th>Recipe</th>
            <th>Can Be Made?</th>
          </tr>
        </thead>
        <tbody>
          {dishes.map(dish => (
            <tr key={dish.id}>
              <td>
                {dish.imageUrl && <img src={dish.imageUrl} alt={dish.name} style={{width: "100px", height: "auto"}}/>}
              </td>
              <td><Link to={`/dish/${dish.id}`}>{dish.name}</Link></td>
              <td>{dish.recipe.substring(0, 100) + (dish.recipe.length > 100 ? "..." : "")}</td>
              <td>{dish.canMake ? "✔" : "ⓧ"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Dishes;

