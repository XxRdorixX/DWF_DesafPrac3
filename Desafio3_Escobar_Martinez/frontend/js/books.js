// Maneja todas las operaciones CRUD (crear, leer, actualizar, eliminar)
// y usa automáticamente el token JWT almacenado en localStorage.

const apiUrl = "http://localhost:8081/api/books";
const token = localStorage.getItem("token"); // 🔒 Se obtiene el token automáticamente del login

document.addEventListener("DOMContentLoaded", () => {
    if (!token) {
        showAlert("Debes iniciar sesión para acceder a los libros.", "danger");
        setTimeout(() => (window.location = "login.html"), 2000);
        return;
    }
    loadBooks();
});

//  Elementos del DOM
const form = document.getElementById("bookForm");
const submitBtn = document.getElementById("submitBtn");
const tableBody = document.getElementById("booksTable");

//  Escucha del formulario (Guardar o Actualizar)
form.addEventListener("submit", handleFormSubmit);

//  Cerrar sesión
function logout() {
    localStorage.removeItem("token");
    showAlert("Sesión cerrada correctamente.", "info");
    setTimeout(() => (window.location = "login.html"), 1500);
}

// Cargar todos los libros
async function loadBooks() {
    try {
        const response = await fetch(apiUrl, {
            headers: { Authorization: `Bearer ${token}` }
        });

        if (!response.ok) throw new Error("Error al cargar los libros.");

        const books = await response.json();
        tableBody.innerHTML = "";

        books.forEach(book => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${book.id}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.publicationYear}</td>
                <td>
                    <button class="btn btn-warning btn-sm me-2" onclick="editBook(${book.id}, '${book.title}', '${book.author}', ${book.publicationYear})">Modificar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteBook(${book.id})">Borrar</button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error(error);
        showAlert("Error al cargar los libros. Verifica el token o inicia sesión nuevamente.", "danger");
    }
}

//  Crear o actualizar libro
async function handleFormSubmit(e) {
    e.preventDefault();
    const id = document.getElementById("bookId").value;
    const title = document.getElementById("title").value.trim();
    const author = document.getElementById("author").value.trim();
    const publicationYear = parseInt(document.getElementById("year").value);

    // Validaciones frontend
    if (!title || !author || !publicationYear) {
        showAlert("Todos los campos son obligatorios.", "warning");
        return;
    }

    //  Evita números negativos o año 0
    if (isNaN(publicationYear) || publicationYear <= 0) {
        showAlert("El año de publicación debe ser un número positivo.", "warning");
        return;
    }

    const book = { title, author, publicationYear };
    const method = id ? "PUT" : "POST";
    const url = id ? `${apiUrl}/${id}` : apiUrl;

    const response = await fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(book)
    });

    if (response.ok) {
        showAlert(id ? "Libro actualizado correctamente." : "Libro agregado correctamente.", "success");
        resetForm();
        loadBooks();
    } else {
        showAlert("Error al guardar el libro. Verifica los datos o el token.", "danger");
    }
}


//  Eliminar libro
async function deleteBook(id) {
    if (!confirm("¿Seguro que deseas borrar este libro?")) return;

    const response = await fetch(`${apiUrl}/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` }
    });

    if (response.ok) {
        showAlert("Libro eliminado correctamente.", "info");
        loadBooks();
    } else {
        showAlert("Error al eliminar el libro.", "danger");
    }
}

//  Cargar datos del libro en el formulario para editar
function editBook(id, title, author, year) {
    document.getElementById("bookId").value = id;
    document.getElementById("title").value = title;
    document.getElementById("author").value = author;
    document.getElementById("year").value = year;

    submitBtn.textContent = "Actualizar";
    submitBtn.classList.remove("btn-success");
    submitBtn.classList.add("btn-primary");
}

// Resetear formulario a modo “Guardar”
function resetForm() {
    form.reset();
    document.getElementById("bookId").value = "";
    submitBtn.textContent = "Guardar";
    submitBtn.classList.remove("btn-primary");
    submitBtn.classList.add("btn-success");
}

// Mostrar alertas visuales (Bootstrap)
function showAlert(message, type = "info") {
    let alertContainer = document.getElementById("alertContainer");
    if (!alertContainer) {
        alertContainer = document.createElement("div");
        alertContainer.id = "alertContainer";
        alertContainer.classList.add("position-fixed", "top-0", "start-50", "translate-middle-x", "p-3");
        document.body.appendChild(alertContainer);
    }

    const alert = document.createElement("div");
    alert.className = `alert alert-${type} alert-dismissible fade show text-center`;
    alert.role = "alert";
    alert.textContent = message;

    setTimeout(() => {
        alert.classList.remove("show");
        alert.classList.add("fade");
        setTimeout(() => alert.remove(), 300);
    }, 2500);

    alertContainer.appendChild(alert);
}
