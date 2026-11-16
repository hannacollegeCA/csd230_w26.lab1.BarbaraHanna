import DiscMag from "./DiscMag";

function DiscMagList({
                         discMags,
                         onDelete,
                         onUpdate
                     }) {
    return (
        <section>
            <h2>Disc Magazines</h2>

            {discMags.map((discMag) => (
                <DiscMag
                    key={discMag.id}
                    discMag={discMag}
                    onDelete={onDelete}
                    onUpdate={onUpdate}
                />
            ))}
        </section>
    );
}

export default DiscMagList;