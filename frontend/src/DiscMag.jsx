import { useState } from "react";

export default function DiscMag({
                                    discMag,
                                    onDelete,
                                    onUpdate
                                }) {

    const [editing, setEditing] = useState(false);
    const [title, setTitle] = useState(discMag.title);

    async function saveChanges() {

        await onUpdate(
            discMag.id,
            {
                ...discMag,
                title: title
            }
        );

        setEditing(false);
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
            <p>Price: ${discMag.price}</p>
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