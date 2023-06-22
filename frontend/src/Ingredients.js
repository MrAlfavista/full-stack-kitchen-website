import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

function Ingredients() {
  const [ingredients, setIngredients] = useState([]);
  const [search, setSearch] = useState('');
  const [count, setCount] = useState('');

  useEffect(() => {
    const fetchIngredients = async () => {
      const response = await axios.get('http://localhost:8080/findAllIngredientsByNameContaining?name=' + search);
      setIngredients(response.data);
    };
    
    fetchIngredients();
  }, [search]);
  

  const addIngredientToUser = async (ingredient_id) => {
    if(localStorage.getItem('user')!==null){
        await axios.post('http://localhost:8080/addUserIngredient', { user_id: localStorage.getItem('userId'), ingredient_id, count });
    }
  };

  return (
    <div>
      <input placeholder="Search" onChange={e => setSearch(e.target.value)} />
      <table>
        <thead>
          <tr>
            <th>Photo</th>
            <th>Name</th>
            <th>Price</th>
            <th>Add</th>
          </tr>
        </thead>
        <tbody>
          {ingredients.map(ingredient => (
            <tr key={ingredient.id}>
              <td><img src={ingredient.imageUrl} alt={ingredient.name} style={{width: "50px", height: "auto"}}/></td>
              <td><Link to={`/ingredient/${ingredient.id}`}>{ingredient.name}</Link></td>
              <td>{ingredient.price}</td>
              <td>
                {localStorage.getItem('user') && 
                  <>
                    <input placeholder="Count" onChange={e => setCount(e.target.value)} />
                    <button onClick={() => addIngredientToUser(ingredient.id)}>Add</button>
                  </>
                }
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Ingredients;


