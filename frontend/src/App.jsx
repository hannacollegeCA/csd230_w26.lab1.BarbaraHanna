import { useState, useEffect } from 'react';
import { Routes, Route, useNavigate } from 'react-router';

import AddBookForm from './AddBookForm.jsx';
import BookList from './BookList.jsx';
import MagazineList from './MagazineList.jsx';
import DiscMagForm from './DiscMagForm.jsx';
import DiscMagList from './DiscMagList.jsx';
import NavBar from './NavBar.jsx';
import Home from './Home.jsx';
import './App.css';
import DisplayCart from './DisplayCart.jsx';

import api from './api.js';

import AuthProvider from './AuthProvider.jsx';
import useAuth from './useAuth.js';
import Login from './Login.jsx';
import ProtectedRoute from './ProtectedRoute.jsx';

export default function App() {
    return (
        <AuthProvider>
            <main className="app-container">
                <h1>My Bookstore Frontend</h1>

                <Routes>
                    <Route
                        path="/login"
                        element={<Login />}
                    />

                    <Route element={<ProtectedRoute />}>
                        <Route
                            path="*"
                            element={<SignedInBookApp />}
                        />
                    </Route>
                </Routes>
            </main>
        </AuthProvider>
    );
}

function SignedInBookApp() {
    const { token } = useAuth();

    return (
        <BookApp key={token} />
    );
}

function BookApp() {

    const navigate = useNavigate();

    const [discMags, setDiscMags] = useState([]);

    const { token, isAdmin } = useAuth();

    const [cart, setCart] = useState(null);
    const [cartLoading, setCartLoading] = useState(true);
    const [cartError, setCartError] = useState('');
    const [cartPending, setCartPending] = useState(false);

    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        api.get('/discmags')
            .then(response => {
                setDiscMags(response.data);
            })
            .catch(console.error);
    }, []);



    const addDiscMag = (discMag) => {
        setDiscMags(previous => [
            ...previous,
            discMag
        ]);

        navigate('/discmags');
    };

    async function handleUpdateDiscMag(id, data) {

        const response =
            await api.put(
                `/discmags/${id}`,
                data
            );

        const savedDiscMag =
            response.data;

        setDiscMags(previous =>
            previous.map(dm =>
                dm.id === id
                    ? savedDiscMag
                    : dm
            )
        );
    }

    async function handleDeleteDiscMag(id) {

        if (
            !window.confirm(
                'Delete this disc magazine?'
            )
        ) {
            return;
        }

        await api.delete(
            `/discmags/${id}`
        );

        setDiscMags(previous =>
            previous.filter(
                dm => dm.id !== id
            )
        );
    }

    useEffect(() => {

        let active = true;

        api.get('/books')
            .then(response => {
                if (active) {
                    setBooks(
                        response.data
                    );
                }
            })
            .catch(err => {
                if (active) {
                    setError(
                        err.message
                    );
                }
            })
            .finally(() => {
                if (active) {
                    setLoading(false);
                }
            });

        return () => {
            active = false;
        };

    }, []);

    useEffect(() => {

        let active = true;

        api.get('/carts/mine')
            .then(response => {
                if (active) {
                    setCart(
                        response.status === 204
                            ? null
                            : response.data
                    );
                }
            })
            .catch(err => {
                if (active) {
                    setCartError(err.message);
                }
            })
            .finally(() => {
                if (active) {
                    setCartLoading(false);
                }
            });

        return () => {
            active = false;
        };

    }, [token]);

    function handleBookAdded(savedBook) {

        setBooks(previous => [
            ...previous,
            savedBook
        ]);

        navigate('/inventory');
    }

    async function handleUpdateBook(
        id,
        data
    ) {

        const response =
            await api.put(
                `/books/${id}`,
                data
            );

        const savedBook =
            response.data;

        setBooks(previous =>
            previous.map(book =>
                book.id === id
                    ? savedBook
                    : book
            )
        );
    }

    async function handleDeleteBook(id) {

        if (
            !window.confirm(
                'Delete this book?'
            )
        ) {
            return;
        }

        setError('');

        try {

            await api.delete(
                `/books/${id}`
            );

            setBooks(previous =>
                previous.filter(
                    book => book.id !== id
                )
            );

        } catch (err) {
            setError(err.message);
        }
    }

    async function changeCart(
        productId,
        remove = false
    ) {

        if (
            cartLoading ||
            cartPending
        ) {
            return;
        }

        setCartError('');
        setCartPending(true);

        try {

            const path =
                `/carts/mine/products/${productId}`;

            const response =
                remove
                    ? await api.delete(path)
                    : await api.post(path);

            setCart(
                response.status === 204
                    ? null
                    : response.data
            );

        } catch (err) {

            setCartError(
                err.message
            );

        } finally {

            setCartPending(false);

        }
    }

    return (
        <>
            <NavBar
                cartCount={cart?.products?.length ?? 0}
            />

            {cartError && (
                <p role="status">
                    Cart: {cartError}
                </p>
            )}

            <Routes>

                <Route
                    path="/"
                    element={<Home />}
                />

                <Route
                    path="/inventory"
                    element={
                        <BookList
                            books={books}
                            loading={loading}
                            error={error}
                            onDelete={handleDeleteBook}
                            onUpdate={handleUpdateBook}
                            canManage={isAdmin}
                            onAddToCart={changeCart}
                            cartPending={cartLoading || cartPending}
                        />
                    }
                />

                <Route
                    path="/add"
                    element={
                        isAdmin ? (
                                <AddBookForm
                                    onBookAdded={handleBookAdded}
                                />
                            ) : (
                                <p role="alert">
                                    Administrator access is required.
                                </p>
                            )
                    }
                />

                <Route
                    path="/magazines"
                    element={<MagazineList
                        onAddToCart={changeCart}
                        cartPending={cartLoading || cartPending}/>}
                />

                <Route
                    path="/discmags"
                    element={
                        <DiscMagList
                            discMags={discMags}
                            onDelete={handleDeleteDiscMag}
                            onUpdate={handleUpdateDiscMag}
                        />
                    }
                />

                <Route
                    path="/add-discmag"
                    element={
                        <DiscMagForm
                            onAdd={addDiscMag}
                        />
                    }
                />

                <Route
                    path="/cart"
                    element={
                        <DisplayCart
                            cart={cart}
                            loading={cartLoading}
                            error={cartError}
                            pending={cartPending}
                            onRemove={(id) =>
                                changeCart(id, true)
                            }
                        />
                    }
                />

                <Route
                    path="*"
                    element={
                        <h2>
                            Page not found
                        </h2>
                    }
                />

            </Routes>
        </>
    );
}