# MobileApp_DevSec

#Charles COPART

- Explain how you ensure user is the right one starting the app

  In order to authenticate correctly the user of the app, I used biometric authentication. The user can either use his fingerprint or his phone's PIN code if 
  he doesn't have a touch sensor. By doing so, I don't have to store any kind of username or password which could be easily stolen.

- How do you securely save user's data on your phone ?

  To save user's data one the phone I use a SQLite database with the Room library, but since it is not directly secured, I used SQLCipher in order to encrypt the database with a passphrase that I encoded and soterd in strings.xml
  
- How did you hide the API url ?

  To hide the API url I encoded it and stored it in styles.xml so it doesn't appear in plain text in my code. To have more security I wanted to store the important strings of     string.xml such as the api url and the passphrase of the database in native code (in a C or C++ file) by implementing NDK to makee it even harder to find with reverse 
  engineering but I didn't manage to make it work before due date.
  
  To add even more security, I used ProGuard in order to build the apk, so all the classes names are changed and data is harder to stole for hackers.
  
  We used the Retrofit library to communicate with the API, and by default it only works with servers with a valid SSL certificate, so TLS is used.  
  
- Screenshots of your application 

  - Login Screen
  
  ![image](https://user-images.githubusercontent.com/64533110/110246806-66ec7c00-7f69-11eb-8609-e7e162fda0ed.png)
  
  - Authentication using touch sensor
  
  ![image](https://user-images.githubusercontent.com/64533110/110246874-a9ae5400-7f69-11eb-8924-5a5e9f45d7c7.png)
  
  - Authentication using PIN

  ![image](https://user-images.githubusercontent.com/64533110/110246831-7bc90f80-7f69-11eb-9382-0420df4cc147.png)

  
  - Accounts page (empty when app is first installed, tap reload button to fetch data from api)
  
  ![image](https://user-images.githubusercontent.com/64533110/110246885-bb8ff700-7f69-11eb-8e43-688693f48730.png)
  
  - Add account page unfilled (unavailable offline)

  ![image](https://user-images.githubusercontent.com/64533110/110246924-eda15900-7f69-11eb-98c3-f5e8d9565d97.png)

  - Add account page filled 

  ![image](https://user-images.githubusercontent.com/64533110/110246945-0742a080-7f6a-11eb-86e8-88e723c081ae.png)

  - Offline warning popup when using tapping buttons on account page
  
  ![image](https://user-images.githubusercontent.com/64533110/110246989-31945e00-7f6a-11eb-8d87-4c95e54ee63e.png)

  
