import React from 'react';
import axios from 'axios';
import { useState } from 'react';

function Register() {
  const [login, setLogin] = useState("");
  const [passwordSha, setPasswordSha] = useState("");

  const register = async () => {
    await axios.post('http://localhost:8080/register', { login, passwordSha });
  };

  return (
    <div>
      <input placeholder="Login" onChange={e => setLogin(e.target.value)} />
      <input placeholder="Password" onChange={e => setPasswordSha(e.target.value)} />
      <button onClick={register}>Register</button>
    </div>
  );
}

export default Register;
