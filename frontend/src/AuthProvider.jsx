import { AuthContext } from './AuthContext.js';
import { hasRole } from './tokenRoles.js';
import { useEffect, useState } from 'react';

export default function AuthProvider({ children }) {

    const [token, setToken] = useState(
        () => localStorage.getItem('token')
    );

    useEffect(() => {

        function invalidSession() {
            setToken(null);
        }

        window.addEventListener(
            'auth:invalid',
            invalidSession
        );

        return () =>
            window.removeEventListener(
                'auth:invalid',
                invalidSession
            );

    }, []);

    function login(nextToken) {

        if (
            typeof nextToken !== 'string' ||
            !nextToken.trim()
        ) {
            throw new Error(
                'Login did not return a token.'
            );
        }

        localStorage.setItem(
            'token',
            nextToken
        );

        setToken(nextToken);
    }

    function logout() {
        localStorage.removeItem('token');
        setToken(null);
    }

    return (
        <AuthContext.Provider
            value={{
                token,
                login,
                logout,
                isAdmin: hasRole(
                    token,
                    'ROLE_ADMIN'
                )
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}