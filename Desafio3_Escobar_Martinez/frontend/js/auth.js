// Módulo encargado del registro e inicio de sesión de usuarios
// Guarda automáticamente el token JWT en localStorage para usarlo en el resto del sistema.

const apiBase = "http://localhost:8081/api/auth";

document.addEventListener("DOMContentLoaded", () => {
    const registerForm = document.getElementById("registerForm");
    const loginForm = document.getElementById("loginForm");

    // REGISTRO DE NUEVO USUARIO
    if (registerForm) {
        registerForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const username = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();

            // Validación simple: ambos campos deben tener contenido
            if (!username || !password) {
                alert("Por favor, completa todos los campos.");
                return;
            }

            try {
                const res = await fetch(`${apiBase}/register`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ username, password })
                });

                const text = await res.text();

                if (res.ok) {
                    alert("Usuario registrado exitosamente.");
                    window.location = "login.html"; // redirige a login
                } else {
                    alert(text || "Error al registrar usuario.");
                }
            } catch (error) {
                console.error("Error en registro:", error);
                alert("Error de conexión con el servidor.");
            }
        });
    }

    // INICIO DE SESIÓN
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const username = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();

            if (!username || !password) {
                alert("Por favor, ingresa usuario y contraseña.");
                return;
            }

            try {
                const res = await fetch(`${apiBase}/login`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ username, password })
                });

                if (res.ok) {
                    // Guarda automáticamente el token JWT
                    const data = await res.json();
                    localStorage.setItem("token", data.token);

                    // Redirige al panel de libros
                    window.location = "books.html";
                } else {
                    alert("Credenciales incorrectas.");
                }
            } catch (error) {
                console.error("Error en inicio de sesión:", error);
                alert("Error de conexión con el servidor.");
            }
        });
    }
});
