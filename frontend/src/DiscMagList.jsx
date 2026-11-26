import DiscMag from "./DiscMag";

function DiscMagList({
                         discMags,
                         onDelete,
                         onUpdate,
                         onAddToCart,
                         cartPending,
                         canManage,
                         loading,
                         error
                     }) {
    return (
        <section>
            <h2>Disc Magazines</h2>

            {loading && <p>Loading Disc Magazines...</p>}

            {error && <p role="alert">{error}</p>}

            {!loading && !error && discMags.length === 0 && (
                <p>No Disc Magazines found.</p>
            )}

            {discMags.map((discMag) => (
                <DiscMag
                    key={discMag.id}
                    discMag={discMag}
                    onDelete={onDelete}
                    onUpdate={onUpdate}
                    onAddToCart={onAddToCart}
                    cartPending={cartPending}
                    canManage={canManage}
                />
            ))}
        </section>
    );
}

export default DiscMagList;