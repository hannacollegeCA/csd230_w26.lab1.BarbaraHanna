import { useState } from "react";

function DiscMagForm({ onAdd }) {
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

    const handleSubmit = async (e) => {
        e.preventDefault();

        const response = await fetch("/api/rest/discmags", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(discMag)
        });

        if (response.ok) {
            const savedDiscMag = await response.json();

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
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Add Disc Magazine</h2>

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

            <button type="submit">
                Save
            </button>
        </form>
    );
}

export default DiscMagForm;