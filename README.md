# Implementation Notes – Disc Magazine CRUD

## Niche Product

The niche product selected for this lab is **DiscMagEntity**, a specialized magazine product that extends the existing MagazineEntity class. The unique field added to this product is `hasDisc`, which indicates whether the magazine includes a disc.

---

## Component Files

The following React components were created/used for the Disc Magazine implementation:

### DiscMagForm.jsx
Responsible for creating new Disc Magazine records.

Features:
- Uses controlled form inputs.
- Sends POST requests to the backend.
- Updates parent state through a callback.
- Redirects to the Disc Magazine list after a successful save.

### DiscMagList.jsx
Responsible for displaying all Disc Magazine records.

Features:
- Receives data through props.
- Uses `.map()` to render each item.
- Uses the database identifier as the React key.

### DiscMag.jsx
Responsible for displaying a single Disc Magazine.

Features:
- Displays all inherited and unique fields.
- Supports updating records through the Edit button.
- Supports deleting records through the Delete button.

---

## Routes

The following routes were added:

### List Route

```text
/discmags
```

Displays all Disc Magazine records.

### Add Route

```text
/add-discmag
```

Displays the Disc Magazine creation form.

---

## API Endpoints

### Retrieve All

```text
GET /api/rest/discmags
```

Returns all Disc Magazine records.

### Retrieve One

```text
GET /api/rest/discmags/{id}
```

Returns a single Disc Magazine.

### Create

```text
POST /api/rest/discmags
```

Creates a new Disc Magazine.

### Update

```text
PUT /api/rest/discmags/{id}
```

Updates an existing Disc Magazine.

### Delete

```text
DELETE /api/rest/discmags/{id}
```

Deletes a Disc Magazine.

---

## JSON Fields and Input Mapping

| Field | Type | Input Handling |
|---------|---------|---------|
| title | String | Text input |
| author | String | Text input |
| price | Double | Number input with parseFloat() |
| copies | Integer | Number input with parseInt() |
| pages | Integer | Number input with parseInt() |
| publisher | String | Text input |
| issueNumber | Integer | Number input with parseInt() |
| hasDisc | Boolean | Checkbox using e.target.checked |

---

## State Ownership

The Disc Magazine collection state is stored in **App.jsx**:

```javascript
const [discMags, setDiscMags] = useState([]);
```

Keeping the collection state in App.jsx allows multiple routes and components to share the same data source.

---

## Props and Callbacks

Data flows from parent to child through props.

Examples:

```javascript
discMags
onUpdate
onDelete
```

Actions flow from child to parent through callbacks.

Examples:

```javascript
handleUpdateDiscMag()
handleDeleteDiscMag()
addDiscMag()
```

This follows React's one-way data flow pattern.

---

## useEffect Usage

The Disc Magazine data is loaded when App.jsx first mounts.

Example:

```javascript
useEffect(() => {
    fetch("/api/rest/discmags")
        .then(response => response.json())
        .then(data => setDiscMags(data));
}, []);
```

The empty dependency array causes the effect to run only once during component initialization.

---

## Immutable Create, Update, and Delete

### Create

```javascript
setDiscMags(previous => [...previous, discMag]);
```

A new array is created instead of modifying the existing state.

### Update

```javascript
setDiscMags(previous =>
    previous.map(dm =>
        dm.id === id ? savedDiscMag : dm
    )
);
```

The updated record replaces the matching record while preserving immutability.

### Delete

```javascript
setDiscMags(previous =>
    previous.filter(dm => dm.id !== id)
);
```

A new array excluding the deleted item is created.

---

## Error Handling Check

Error handling was implemented by checking `response.ok` after API requests.

Example:

```javascript
if (!response.ok) {
    throw new Error("Update failed");
}
```

This prevents false success messages and ensures invalid requests are properly handled.

---

## Build Result

The frontend build was verified successfully using:

```bash
npm run build
```

The project compiled successfully without build errors.

---

## Verification

CRUD functionality was successfully tested:

- Create (POST)
- Read (GET)
- Update (PUT)
- Delete (DELETE)

Data persistence was verified by reading the records again through:

```text
http://localhost:8080/api/rest/discmags
```

Changes made through React were successfully stored and returned by the Spring Boot backend.