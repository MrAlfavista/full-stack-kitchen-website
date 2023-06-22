import React, { useState, useRef } from 'react';
import axios from 'axios';

function CreateDish() {
  const [name, setName] = useState("");
  const [recipe, setRecipe] = useState("");
  const [ingredients, setIngredients] = useState([]);
  const [search, setSearch] = useState("");
  const [selectedIngredients, setSelectedIngredients] = useState([]);
  const [totalCalories, setTotalCalories] = useState(0);
  const [totalPrice, setTotalPrice] = useState(0);
  const [count, setCount] = useState({});
  const [image, setImage] = useState(null);  // Added this line
  const inputFile = useRef(null);

  const createDish = async () => {
    const dish = {
      name, 
      recipe, 
      calories: totalCalories, 
      price: totalPrice
    };

    const formData = new FormData();
    formData.append("file", image);
    const uploadRes = await axios.post('http://localhost:8080/uploadDishImage', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    if (uploadRes.status === 200) {
      dish.imageUrl = uploadRes.data;
    }
    const response = await axios.post('http://localhost:8080/createDish', dish);
    const dishId = response.data;
    selectedIngredients.forEach(async ingredient => {
        await axios.post('http://localhost:8080/addDishIngredient', { 
            dish_id: dishId, 
            ingredient_id: ingredient.id, 
            count: count[ingredient.id]
        });
    });
  };

  const onImageChange = (e) => {
    setImage(e.target.files[0]);
  };

  const searchIngredients = async () => {
    const response = await axios.get('http://localhost:8080/findAllIngredientsByNameContaining', {
        params: {
            name: search
        }
    });
    setIngredients(response.data);
  };

  const addIngredient = ingredient => {
    const existingIngredient = selectedIngredients.find(item => item.id === ingredient.id);

    if(!existingIngredient) {
      setSelectedIngredients([...selectedIngredients, ingredient]);
    }

    setTotalCalories(totalCalories + ingredient.calories*count[ingredient.id]/ingredient.amount);
    setTotalPrice(totalPrice + ingredient.price*count[ingredient.id]/ingredient.amount);
  };

  const deleteIngredient = ingredient => {
    setSelectedIngredients(selectedIngredients.filter(item => item.id !== ingredient.id));
    setTotalCalories(totalCalories - ingredient.calories * count[ingredient.id]/ingredient.amount);
    setTotalPrice(totalPrice - ingredient.price * count[ingredient.id]/ingredient.amount);
  };

  const updateCount = (id, value) => {
    setCount({
      ...count,
      [id]: value
    });
  };

  return (
    <div>
      <input placeholder="Name" onChange={e => setName(e.target.value)} />
      <input placeholder="Recipe" onChange={e => setRecipe(e.target.value)} />
      <input type="file" onChange={onImageChange} ref={inputFile} style={{display: 'none'}} />
      <button onClick={() => inputFile.current.click()}>Upload Image</button>
      <button onClick={createDish}>Create Dish</button>

      <input placeholder="Search Ingredients" onChange={e => setSearch(e.target.value)} />
      <button onClick={searchIngredients}>Search</button>

      {ingredients.map(ingredient => (
        <div key={ingredient.id}>
          <span>{ingredient.name}</span>
          <input 
            type="number" 
            value={count[ingredient.id] || 0} 
            onChange={(e) => updateCount(ingredient.id, +e.target.value)}
          />
          <button onClick={() => addIngredient(ingredient)}>Add</button>
        </div>
      ))}

      <h2>Selected Ingredients</h2>
      {selectedIngredients.map(ingredient => (
        <div key={ingredient.id}>
          <span>{ingredient.name} - {count[ingredient.id] || 0}</span>
          <button onClick={() => deleteIngredient(ingredient)}>Delete</button>
        </div>
      ))}
    </div>
  );
}

export default CreateDish;


