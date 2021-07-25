## To Do List - Santander Bootcamp



##### Santander bootcamp final project with some improvements



#### Improvements:

- Status task
- Task summary and description
- Field validation
- Persistence using ROOM



##### Dependencies:



```
ext{
    roomVersion = '2.2.5'
    lifecycleVersion = '2.2.0'
}
```



```
implementation "androidx.room:room-ktx:$rootProject.roomVersion"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$rootProject.lifecycleVersion"
implementation "androidx.lifecycle:lifecycle-livedata-ktx:$rootProject.lifecycleVersion"
kapt "androidx.room:room-compiler:$rootProject.roomVersion"io.coil-kt:coil:1.2.0
```



<img src="./readme/home.PNG" width="200" height="400" />			 <img src="./readme/newtask.png" width="200" height="400" />			 <img src="./readme/homewithtask.png" width="200" height="400" />  <img src="./readme/homewithfinishtask.png" width="200" height="400" /><img src="./readme/edittask.png" width="200" height="400" />

---


