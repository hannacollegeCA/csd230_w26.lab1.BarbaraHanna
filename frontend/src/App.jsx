import { useState, useEffect } from 'react';
import AddBookForm from './AddBookForm.jsx';
import BookList from './BookList.jsx';
import './App.css';
import { Routes, Route, useNavigate } from 'react-router';
import NavBar from './NavBar.jsx';
import Home from './Home.jsx';
import MagazineList from './MagazineList.jsx';
import DiscMagForm from "./DiscMagForm";
import DiscMagList from "./DiscMagList";
import api from './api.js';


export default function App() {
    const navigate = useNavigate();
    const [discMags, setDiscMags] = useState([]);
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    // DiscMags
    useEffect(() => {
        api.get("/discmags")
            .then((response) => setDiscMags(response.data))
            .catch(err => console.error(err));
    }, []);

    const addDiscMag = (discMag) => {
        setDiscMags(previous => [...previous, discMag]);
        navigate("/discmags");
    };

    async function handleUpdateDiscMag(id, data) {
        const response = await api.put(
            `/discmags/${id}`,
            data
        );


        const savedDiscMag = response.data;

        setDiscMags(previous =>
            previous.map(dm =>
                dm.id === id ? savedDiscMag : dm
            )
        );
    }

    async function handleDeleteDiscMag(id) {

        if (!window.confirm("Delete this disc magazine?")) {
            return;
        }

        await api.delete(`/discmags/${id}`);

        setDiscMags(previous =>
            previous.filter(dm => dm.id !== id)
        );
    }

    useEffect(() => {
        let active = true;

        api.get('/books')
            .then(response => {
                if (active) setBooks(response.data);
            })
            .catch(err => {
                if (active) setError(err.message);
            })
            .finally(() => {
                if (active) setLoading(false);
            });

        return () => {
            active = false;
        };
    }, []);



    function handleBookAdded(savedBook) {
        setBooks(previous => [...previous, savedBook]);
        navigate('/inventory');
    }

    async function handleUpdateBook(id, data) {
        const response = await api.put(`/books/${id}`, data);

        const savedBook = response.data;

        setBooks(previous =>
            previous.map(book =>
                book.id === id ? savedBook : book
            )
        );
    }

    async function handleDeleteBook(id) {
        if (!window.confirm('Delete this book?')) return;

        setError('');

        try {await api.delete(`/books/${id}`);

            setBooks(previous =>
                previous.filter(book => book.id !== id)
            );

        } catch (err) {
            setError(err.message);
        }
    }

    return (
        <main className="app-container">
            <h1>My Bookstore Frontend</h1>

            <NavBar/>

            <Routes>
                <Route
                    path="/"
                    element={<Home/>}
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
                        />
                    }
                />

                <Route
                    path="/add"
                    element={
                        <AddBookForm
                            onBookAdded={handleBookAdded}
                        />
                    }
                />

                <Route
                    path="/magazines"
                    element={<MagazineList />}
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
                    element={<DiscMagForm onAdd={addDiscMag} />}
                />

                <Route
                    path="*"
                    element={<h2>Page not found</h2>}
                />

            </Routes>
        </main>
    );

}