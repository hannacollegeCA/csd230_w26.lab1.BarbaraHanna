import { useState } from "react";

export default function DiscMag({
                                    discMag,
                                    onDelete,
                                    onUpdate
                                }) {

    const [editing, setEditing] = useState(false);
    const [title, setTitle] = useState(discMag.title);
    const [author, setAuthor] = useState(discMag.author);
    const [price, setPrice] = useState(discMag.price);
    const [copies, setCopies] = useState(discMag.copies);
    const [pages, setPages] = useState(discMag.pages);
    const [publisher, setPublisher] = useState(discMag.publisher);
    const [issueNumber, setIssueNumber] = useState(discMag.issueNumber);
    const [hasDisc, setHasDisc] = useState(discMag.hasDisc);

    async function saveChanges() {

        try {
            await onUpdate(
                discMag.id,
                {
                    ...discMag,
                    title,
                    author,
                    price: Number(price),
                    copies: Number(copies),
                    pages: Number(pages),
                    publisher,
                    issueNumber: Number(issueNumber),
                    hasDisc
                }
            );

            setEditing(false);
        } catch (err) {
            console.error(err);
        }
    }

    if (editing) {
        return (
            <article>

                <input
                    value={title}
                    onChange={(e) =>
                        setTitle(e.target.value)
                    }
                />

                <input
                    value={author}
                    onChange={(e) => setAuthor(e.target.value)}
                    placeholder="Author"
                />

                <input
                    type="number"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    placeholder="Price"
                />

                <input
                    type="number"
                    value={copies}
                    onChange={(e) => setCopies(e.target.value)}
                    placeholder="Copies"
                />

                <input
                    type="number"
                    value={pages}
                    onChange={(e) => setPages(e.target.value)}
                    placeholder="Pages"
                />

                <input
                    value={publisher}
                    onChange={(e) => setPublisher(e.target.value)}
                    placeholder="Publisher"
                />

                <input
                    type="number"
                    value={issueNumber}
                    onChange={(e) => setIssueNumber(e.target.value)}
                    placeholder="Issue Number"
                />

                <label>
                    Has Disc
                    <input
                        type="checkbox"
                        checked={hasDisc}
                        onChange={(e) => setHasDisc(e.target.checked)}
                    />
                </label>

                <button
                    onClick={saveChanges}
                >
                    Save
                </button>

                <button
                    onClick={() =>
                        setEditing(false)
                    }
                >
                    Cancel
                </button>

            </article>
        );
    }

    return (
        <article>

            <h3>{discMag.title}</h3>

            <p>Author: {discMag.author}</p>
            <p>Publisher: {discMag.publisher}</p>
            <p>Issue: {discMag.issueNumber}</p>
            <p>Pages: {discMag.pages}</p>
            <p>Price: ${Number(discMag.price).toFixed(2)}</p>
            <p>Copies: {discMag.copies}</p>

            <p>
                Disc Included:
                {discMag.hasDisc ? " Yes" : " No"}
            </p>

            <button
                onClick={() =>
                    setEditing(true)
                }
            >
                Edit
            </button>

            <button
                onClick={() =>
                    onDelete(discMag.id)
                }
            >
                Delete
            </button>

        </article>
    );
}