import React, { useState, useRef } from 'react';
import axios from 'axios';

const AddTrademark = ({ match }) => {
  const [name, setName] = useState("");
  const [price, setPrice] = useState(0);
  const [calories, setCalories] = useState(0);
  const [measure, setMeasure] = useState("грамм");
  const [amount, setAmount] = useState(0);
  const [ingredient, setIngredient] = useState({});
  const [image, setImage] = useState(null); 
  const inputFile = useRef(null);

  const measures = ["грамм", "шт", "мл"];

  const addTrademark = async () => {
    const trademark = {
      name,
      price,
      calories,
      measure,
      ingredient,
      amount
    };

    setIngredient({id: match.params.id});
    const formData = new FormData();
    formData.append("file", image);
    const uploadRes = await axios.post('http://localhost:8080/uploadTrademarkImage', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    if (uploadRes.status === 200) {
      trademark.imageUrl = uploadRes.data;
      await axios.post('http://localhost:8080/addTrademark', trademark);
    }
  };

  const onImageChange = (e) => {
    setImage(e.target.files[0]);
  };

  return (
    <div>
      <input placeholder="Name" onChange={e => setName(e.target.value)} />
      <input placeholder="Price" onChange={e => setPrice(e.target.value)} />
      <input placeholder="Amount" onChange={e => setAmount(e.target.value)} />
      <select placeholder="Measure" onChange={e => setMeasure(e.target.value)} >
        {measures.map((measure, index) => (
          <option key={index} value={measure}>{measure}</option>
        ))}
      </select>
      <input placeholder="Calories" onChange={e => setCalories(e.target.value)} />
      <input type="file" onChange={onImageChange} ref={inputFile} style={{display: 'none'}} />
      <button onClick={() => inputFile.current.click()}>Upload Image</button>
      <button onClick={addTrademark}>Add Trademark</button>
    </div>
  );
}

export default AddTrademark;