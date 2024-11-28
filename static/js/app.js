// // app.js

// document.addEventListener("DOMContentLoaded", function () {

//     // Обработчик для добавления нового фильма через форму
//     const createForm = document.getElementById("create-form");
//     if (createForm) {
//         createForm.addEventListener("submit", function (event) {
//             event.preventDefault();

//             const title = document.getElementById("title").value;
//             const director = document.getElementById("director").value;
//             const year = document.getElementById("year").value;

//             const filmData = {
//                 title: title,
//                 director: director,
//                 year: year
//             };

//             // Отправляем POST запрос для добавления фильма
//             fetch("/films", {
//                 method: "POST",
//                 headers: {
//                     "Content-Type": "application/x-www-form-urlencoded",
//                 },
//                 body: new URLSearchParams(filmData).toString(),
//             })
//             .then(response => response.json())
//             .then(data => {
//                 // Обновляем список фильмов на странице после добавления
//                 location.reload();
//             })
//             .catch(error => {
//                 console.error('Error adding film:', error);
//             });
//         });
//     }

//     // Обработчик для удаления фильма
//     const deleteButtons = document.querySelectorAll(".delete-button");
//     deleteButtons.forEach(button => {
//         button.addEventListener("click", function () {
//             const filmId = button.dataset.id;

//             // Отправляем запрос на удаление фильма
//             fetch(`/films/${filmId}/delete`, {
//                 method: "POST",
//             })
//             .then(response => {
//                 if (response.ok) {
//                     // Удаляем фильм из списка на странице
//                     button.closest("li").remove();
//                 }
//             })
//             .catch(error => {
//                 console.error('Error deleting film:', error);
//             });
//         });
//     });

//     // Обработчик для редактирования фильма
//     const editForms = document.querySelectorAll(".edit-form");
//     editForms.forEach(form => {
//         form.addEventListener("submit", function (event) {
//             event.preventDefault();

//             const filmId = form.dataset.id;
//             const title = form.querySelector(".title").value;
//             const director = form.querySelector(".director").value;
//             const year = form.querySelector(".year").value;

//             const updatedData = {
//                 title: title,
//                 director: director,
//                 year: year
//             };

//             // Отправляем PUT запрос для обновления фильма
//             fetch(`/films/${filmId}/edit`, {
//                 method: "POST",
//                 headers: {
//                     "Content-Type": "application/x-www-form-urlencoded",
//                 },
//                 body: new URLSearchParams(updatedData).toString(),
//             })
//             .then(response => response.json())
//             .then(data => {
//                 // Обновляем список фильмов на странице после редактирования
//                 location.reload();
//             })
//             .catch(error => {
//                 console.error('Error updating film:', error);
//             });
//         });
//     });

// });