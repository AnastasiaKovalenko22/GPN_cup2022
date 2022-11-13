# GPN_cup2022
## Описание приложения
Интеграционный RESTful cloud-native сервис на Java для получения ФИО пользователя VK, а также признака участника группы VK.

В приложении есть 2 endpoint-а:
1) /register - для регистраци пользователя в приложении по логину и паролю;
2) /isMember - для получения ФИО пользователя VK, а также признака участника группы VK.

### Подробное описание endpoint-ов находится в файле openapi.yaml

## Инструкция по сборке и деплою приложения (локально в minikube)

### Сборка
Чтобы собрать jar файл приложеня, выполнните команду:

<code>mvn clean package</code>

### Деплой в minikube

1)Переместить файл vk_case-0.0.1-SNAPSHOT.jar(получен при сборке) из папки target в папку docker
   
2)Перейти в папку docker: <code>cd {путь до папки с проектом}/src/main/docker</code>
   
3)Выполнить команду, чтобы собрался docker образ приложения (vk-case:latest): <code>sudo docker-compose up</code>

4)Переименовать docker образ: <code>sudo docker tag vk-case:latest {your docker hub username}/vk-case</code>
   
5)Запушить docker образ приложения в свой docker hub: <code>sudo docker push {your docker hub username}/vk-case</code>
   
6)В файле app-deployment.yaml заменить строку <code>image: kovalenko22/vk-case</code> на <code>image: {your docker hub username}/vk-case</code>
   
7)Перейти в папку k8s: <code>cd {путь до папки с проектом}/src/main/k8s</code>
   
8)Запустить minikube: <code>minikube start --driver=kvm2</code>

9)Создать pod базы данных: <code>kubectl apply -f db-deployment.yaml</code>

10)Создать pod приложения: <code>kubectl apply -f app-deployment.yaml</code>

11)Проверить, что оба pod-а находятся в статусе running: <code>kubectl get pods --output=wide</code>

12)Получить url приложения: <code>minikube service vk-case --url</code>

Далее отправить запросы к приложению можно по ссылкам: <code>{url приложения}/register</code> и <code>{url приложения}/isMember</code>
