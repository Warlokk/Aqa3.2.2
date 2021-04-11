# Повторный запуск SUT

Для повторного запуска SUT необходимо запускать контейнер mySql с флагом -V,
с целью повторной инициализации и очистки записей в базе данных

``` docker-compose up -V ```

Для запуска SUT используется команда:

```java -jar app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/appdb -P:jdbc.user=user -P:jdbc.password=pass```