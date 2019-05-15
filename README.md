# Loci
Команда Bigas (гр. 650502)
* Бордак Анастасия - скрам-мастер, business-analytics
* Квятиская Анастасия - помощник скрам-мастера, software engineer 
* Высоцкая Милена - software engineer
* Кабринович Евгений - designer
# Описание
Loci - проект для расширения словарного запаса на английском. Пользователь сможет тренировать карточки с использованием различных методов: сопоставить карточку с её значением, изображением или собрать изучаемое слово из частей. Также пользователь сможет просмотреть все существующие на текущий момент.
# Технологии
Java, MySQL 
# [Dashboard](https://trello.com/b/MJmoT6JF/bigas)
# [PlanningPocker](https://play.planningpoker.com/play/game/YjlRoLAd1b59TqEGoOXF4dw8fAMCyyUt)
# [Выбор названия](https://goo.gl/forms/aYxLI051cNnhsHCr1)
# [Плохой скрам-мастер](https://goo.gl/forms/62OnkV5bjACXl06I2)
# [Парное программирование](https://github.com/Anastasiyabordak/Loci/blob/master/pair_programming.md)
# [Анализ архитектуры](https://github.com/Anastasiyabordak/Loci/blob/master/doc/arch.md)
# [Технический долг](https://github.com/Anastasiyabordak/Loci/blob/master/doc/debt.md)
# [UX](https://github.com/Anastasiyabordak/Loci/blob/master/doc/UX.md)
# Спринты
* 09.02.2019 - 02.03.2019 Создание тренировки "картинка+описание"
* 02.03.2019 - 23.03.2019 Создание тренировок "собери слово из слогов", "картинка", "описание, а также просмотра карточек
* 23.03.2019 - 13.04.2019 Создание тренировки "выбор из 4-ёх слов", рефакторинг, переход среди приложения по нажатию ENTER
* 16.04.2019 - 04.05.2019 Создание тренировки "на правописание", работа с UX


~/testProgect/src/sample/cards/- директория описания карточек, где каждый файл - все карточки одной категории, имя файла - название категории <br> 
all.txt - файл, где хранятся имена файлов всех категорий <br> 
Формат записи карточки: <br> 
"card_id", "card_name", "card_definition", "image_location" <br> 
~/testProgect/src/sample/image/ - директория, где хранятся все изображения,название директории - название категории <br> 
