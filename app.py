from flask import Flask, jsonify, request, render_template, redirect, url_for
from instance.bd import get_db, init_db
from utils import log_execution_time  # Импортируем декоратор

app = Flask(__name__)

# Инициализация базы данных с контекстом приложения
with app.app_context():
    init_db()

@app.route('/')
@log_execution_time
def index():
    """Главная страница для отображения списка фильмов"""
    db = get_db()
    films = db.execute('SELECT * FROM films').fetchall()
    return render_template('index.html', films=films)

@app.route('/films/<int:id>', methods=['GET'])
@log_execution_time
def get_film(id):
    """Получить фильм по ID"""
    db = get_db()
    film = db.execute('SELECT * FROM films WHERE id = ?', (id,)).fetchone()
    if film is None:
        return jsonify({"error": "Film not found"}), 404
    return jsonify(dict(film))

@app.route('/films', methods=['POST'])
@log_execution_time
def create_film():
    """Создать новый фильм"""
    new_film = request.form
    title = new_film.get("title")
    director = new_film.get("director")
    year = new_film.get("year")
    
    db = get_db()
    db.execute('INSERT INTO films (title, director, year) VALUES (?, ?, ?)', (title, director, year))
    db.commit()
    return redirect(url_for('index'))

@app.route('/films/<int:id>/edit', methods=['GET', 'POST'])
@log_execution_time
def update_film(id):
    """Обновить информацию о фильме (метод PUT)"""
    db = get_db()
    if request.method == 'POST':  # Обычно обновление происходит через PUT, но мы обработаем через POST
        updated_film = request.form
        title = updated_film.get("title")
        director = updated_film.get("director")
        year = updated_film.get("year")
        
        db.execute('UPDATE films SET title = ?, director = ?, year = ? WHERE id = ?', 
                (title, director, year, id))
        db.commit()
        return redirect(url_for('index'))
    
    film = db.execute('SELECT * FROM films WHERE id = ?', (id,)).fetchone()
    if film is None:
        return jsonify({"error": "Film not found"}), 404
    return render_template('edit_film.html', film=film)

@app.route('/films/<int:id>', methods=['PUT'])
@log_execution_time
def put_film(id):
    """Обновить фильм с использованием метода PUT"""
    updated_film = request.json
    title = updated_film.get("title")
    director = updated_film.get("director")
    year = updated_film.get("year")
    
    db = get_db()
    db.execute('UPDATE films SET title = ?, director = ?, year = ? WHERE id = ?', 
            (title, director, year, id))
    db.commit()
    return jsonify({"message": "Film updated successfully!"})

@app.route('/films/<int:id>', methods=['DELETE'])
@log_execution_time
def delete_film(id):
    """Удалить фильм по ID с использованием метода DELETE"""
    db = get_db()
    db.execute('DELETE FROM films WHERE id = ?', (id,))
    db.commit()
    return jsonify({"message": "Film deleted successfully!"})

if __name__ == '__main__':
    app.run(debug=True)
