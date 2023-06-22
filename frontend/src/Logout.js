import React  from 'react';

function Logout() {

  const logOut = async () => {
        localStorage.removeItem('user');
        localStorage.removeItem('userId');
     };

  return (
    <div>
      <button onClick={logOut}>LogOut</button>
    </div>
  );
}

export default Logout;