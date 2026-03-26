import React, { createContext, useContext, useState, useEffect } from 'react';
import api from '../api/axios';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    try {
      const stored = localStorage.getItem('lms_user');
      return stored ? JSON.parse(stored) : null;
    } catch { return null; }
  });

  const login = async (username, password) => {
    try {
      const response = await api.post('/auth/login', { username, password });
      if (response.data.success) {
        const { token, type, ...safeUser } = response.data.data;
        // Roles is an array like [{ id: 1, name: "ROLE_ADMIN" }] or string array
        // In the original demo, role was 'admin', 'faculty', 'staff'
        const isAdmin = safeUser.roles && safeUser.roles.some(r => r === 'ROLE_ADMIN' || r.name === 'ROLE_ADMIN');
        
        // Add a computed role string for frontend compatibility if needed
        safeUser.role = isAdmin ? 'admin' : 'staff'; 

        localStorage.setItem('lms_token', token);
        localStorage.setItem('lms_user', JSON.stringify(safeUser));
        setUser(safeUser);
        
        return { success: true };
      }
      return { success: false, error: response.data.message || 'Login failed' };
    } catch (error) {
      console.error("Login error", error);
      const errMsg = error.response?.data?.message || 'Invalid credentials or server error';
      return { success: false, error: errMsg };
    }
  };

  const logout = () => {
    localStorage.removeItem('lms_token');
    localStorage.removeItem('lms_user');
    setUser(null);
    window.location.href = '/LabManagementSystem/login'; // Force redirect to avoid state caching issues
  };

  // Original UI checks `isAdmin: user?.role === 'admin'` so we maintain that
  return (
    <AuthContext.Provider value={{ user, login, logout, isAdmin: user?.role === 'admin' }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
