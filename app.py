from flask import Flask, jsonify, request, render_template, redirect, url_for
from instance.bd import get_db, init_db

app = Flask(__name__)

# Инициализация базы данных с контекстом приложения
with app.app_context():
    init_db()

@app.route('/')
def index():
    """Главная страница для отображения списка фильмов"""
    db = get_db()
    films = db.execute('SELECT * FROM films').fetchall()
    return render_template('index.html', films=films)

# **READ**: Получить фильм по ID
@app.route('/films/<int:id>', methods=['GET'])
def get_film(id):
    """Получить фильм по ID"""
    db = get_db()
    film = db.execute('SELECT * FROM films WHERE id = ?', (id,)).fetchone()
    if film is None:
        return jsonify({"error": "Film not found"}), 404
    return jsonify(dict(film))

# **CREATE**: Добавить новый фильм
@app.route('/films', methods=['POST'])
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

# **UPDATE**: Обновить фильм по ID
@app.route('/films/<int:id>/edit', methods=['GET', 'POST'])
def update_film(id):
    """Обновить информацию о фильме"""
    db = get_db()
    if request.method == 'POST':
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

# **DELETE**: Удалить фильм по ID
@app.route('/films/<int:id>/delete', methods=['POST'])
def delete_film(id):
    """Удалить фильм по ID"""
    db = get_db()
    db.execute('DELETE FROM films WHERE id = ?', (id,))
    db.commit()
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run(debug=True)
