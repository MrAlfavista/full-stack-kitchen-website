import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const IngredientDetails = ({ match }) => {
  const [ingredient, setIngredient] = useState({});
  const [trademarks, setTrademarks] = useState([]);

  useEffect(() => {
    const fetchTrademarks = async () => {
      const response = await axios.get(`http://localhost:8080/getIngredientTrademarks?ingredientId=${match.params.id}`);
      setTrademarks(response.data);
    };

    fetchTrademarks();
  }, [match.params.id]);

  useEffect(() => {
    const fetchIngredient = async () => {
      const response = await axios.get(`http://localhost:8080/findById?id=${match.params.id}`);
      setIngredient(response.data);
    };
    fetchIngredient();
  }, [match.params.id]);

  return (
    <div>
      <h1>{ingredient.name}</h1>
      <p>{}</p>
      <p>Calories: {ingredient.calories}</p>
      <p>Price: {ingredient.price}</p>
      <td><Link to={`/addTrademark/${ingredient.id}`}>Add Trademark</Link></td>
      {trademarks.map((trademark, index) => (
        <tr key={index}>
          <td>{trademark.imageUrl && 
            <img src={trademark.imageUrl} alt={trademark.name} style={{width: "200px", height: "auto"}}/>}</td>
          <td>{trademark.name}, {trademark.count} {trademark.measure}</td>
        </tr>
      ))}
    </div>
  );
};

export default IngredientDetails;
