import React, { useState } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

function Login() {
  const [login, setLogin] = useState("");
  const [passwordSha, setPasswordSha] = useState("");
  const history = useHistory();

  const loggingIn = async () => {
    const response = await axios.post('http://localhost:8080/login', { login, passwordSha });
    const user = response.data;
    if(user !== ''){
        alert(user);
        localStorage.setItem('user', user.login);
        localStorage.setItem('userId', user.id);
        history.push('/');
        alert("Welcome Back!");
    } else {
        alert(user.login);
        alert("Please enter valid Login and Password");
    }
  };

  return (
    <div>
      <input placeholder="Login" onChange={e => setLogin(e.target.value)} />
      <input placeholder="PasswordSha" type="passwordSha" onChange={e => setPasswordSha(e.target.value)} />
      <button onClick={loggingIn}>Login</button>
    </div>
  );
}

export default Login;
