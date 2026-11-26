import { useState } from "react";
import useAuth from "./useAuth.js";
import api from "./api.js";

function DiscMagForm({ onAdd }) {
    const { token } = useAuth();
    const [discMag, setDiscMag] = useState({
        title: "",
        author: "",
        price: 0,
        copies: 0,
        pages: 0,
        publisher: "",
        issueNumber: 0,
        hasDisc: false
    });

    const [error, setError] = useState("");
    const [saving, setSaving] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const requestToken = token;

        const isCurrent = () =>
            requestToken &&
            globalThis.localStorage?.getItem("token") === requestToken;

        if (!isCurrent()) {
            setError("Your session is no longer valid. Please log in again.");
            return;
        }

        setError("");
        setSaving(true);

        try {
            const response = await api.post(
                "/discmags",
                discMag
            );

            if (!isCurrent()) return;



        const savedDiscMag = response.data;

            onAdd(savedDiscMag);

            setDiscMag({
                title: "",
                author: "",
                price: 0,
                copies: 0,
                pages: 0,
                publisher: "",
                issueNumber: 0,
                hasDisc: false
            });

    } catch (err) {
        if (isCurrent()) {
            setError(
                err.response?.status === 403
                    ? "You do not have permission to add Disc Magazines."
                    : "Unable to save the Disc Magazine. Please try again."
            );
        }
    } finally {
        if (isCurrent()) {
            setSaving(false);
        }
    }
};

    return (
        <form onSubmit={handleSubmit}>
            <h2>Add Disc Magazine</h2>

            {error && <p role="alert">{error}</p>}

            <input
                placeholder="Title"
                value={discMag.title}
                onChange={(e) =>
                    setDiscMag({ ...discMag, title: e.target.value })
                }
            />

            <input
                placeholder="Author"
                value={discMag.author}
                onChange={(e) =>
                    setDiscMag({ ...discMag, author: e.target.value })
                }
            />

            <input
                type="number"
                placeholder="Price"
                value={discMag.price}
                onChange={(e) =>
                    setDiscMag({
                        ...discMag,
                        price: parseFloat(e.target.value)
                    })
                }
            />

            <input
                type="number"
                placeholder="Copies"
                value={discMag.copies}
                onChange={(e) =>
                    setDiscMag({
                        ...discMag,
                        copies: parseInt(e.target.value)
                    })
                }
            />

            <input
                type="number"
                placeholder="Pages"
                value={discMag.pages}
                onChange={(e) =>
                    setDiscMag({
                        ...discMag,
                        pages: parseInt(e.target.value)
                    })
                }
            />

            <input
                placeholder="Publisher"
                value={discMag.publisher}
                onChange={(e) =>
                    setDiscMag({
                        ...discMag,
                        publisher: e.target.value
                    })
                }
            />

            <input
                type="number"
                placeholder="Issue Number"
                value={discMag.issueNumber}
                onChange={(e) =>
                    setDiscMag({
                        ...discMag,
                        issueNumber: parseInt(e.target.value)
                    })
                }
            />

            <label>
                Has Disc
                <input
                    type="checkbox"
                    checked={discMag.hasDisc}
                    onChange={(e) =>
                        setDiscMag({
                            ...discMag,
                            hasDisc: e.target.checked
                        })
                    }
                />
            </label>

            <button type="submit" disabled={saving}>
                {saving ? "Saving..." : "Save"}
            </button>
        </form>
    );
}

export default DiscMagForm;