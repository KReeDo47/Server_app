import sqlite3
from flask import g
from flask import current_app

DATABASE = 'instance/films.db'

def get_db():
    """Подключение к базе данных"""
    if 'db' not in g:
        g.db = sqlite3.connect(DATABASE)
        g.db.row_factory = sqlite3.Row  # Чтобы можно было обращаться к колонкам по имени
    return g.db

def init_db():
    """Инициализация базы данных"""
    db = get_db()
    with current_app.open_resource('instance/schema.sql', mode='r') as f:

        db.cursor().executescript(f.read())
    db.commit()
