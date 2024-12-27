# utils.py
import time
import logging
from functools import wraps

# Настройка логирования
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(message)s')
logger = logging.getLogger(__name__)

# Декоратор для логирования времени выполнения
def log_execution_time(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        start_time = time.time()  # Время начала выполнения
        result = func(*args, **kwargs)  # Выполнение функции
        end_time = time.time()  # Время окончания выполнения
        execution_time = (end_time - start_time) * 1000  # Время в миллисекундах
        logger.info(f"Время выполнения метода '{func.__name__}': {execution_time:.2f} ms")
        return result
    return wrapper
