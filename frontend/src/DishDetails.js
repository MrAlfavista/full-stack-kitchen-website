import React, { useEffect, useState } from 'react';
import axios from 'axios';

const DishDetails = ({ match }) => {
  const [dish, setDish] = useState({});
  const [dishIngredients, setDishIngredients] = useState([]);

  useEffect(() => {
    const fetchDishIngredients = async () => {
      const response = await axios.get(`http://localhost:8080/getDishIngredients?dishId=${match.params.id}`);
      setDishIngredients(response.data);
    };

    fetchDishIngredients();
  }, [match.params.id]);

  useEffect(() => {
    const fetchDish = async () => {
      const response = await axios.get(`http://localhost:8080/getDish?id=${match.params.id}`);
      setDish(response.data);
    };
    fetchDish();
  }, [match.params.id]);

  return (
    <div>
      <h1>{dish.name}</h1>
      <p>{dish.recipe}</p>
      <p>Calories: {dish.calories}</p>
      <p>Price: {dish.price}</p>
      {dishIngredients.map((ingredient, index) => (
        <tr key={index}>
          <td>{ingredient.ingredient.imageUrl && 
            <img src={ingredient.ingredient.imageUrl} alt={ingredient.ingredient.name} style={{width: "50px", height: "auto"}}/>}</td>
          <td>{ingredient.ingredient.name}, {ingredient.count} {ingredient.ingredient.measure}</td>
        </tr>
      ))}
    </div>
  );
};

export default DishDetails;

