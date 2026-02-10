# Refleksi 1

clean code principles yang telah dipelajari untuk membuat code ini:
1. meaningful names, untuk penggunaan nama kelas serta variable kita harus menggunakan nama yang menjelaskan secara jelas mereka itu nanti untuk apa dan mengikuti standar dari spring boot juga, terkhusus untuk variabel ia harus deskriptif dan menunjukkan bahwa namanya emang fungsinya untuk itu sehingga kita ngga perlu pake comment lagi
2. Single Responsibility Principle:
    kita juga harus memisahkan sesuai dengan kegunaan seperti misal controller, service, repository, dan model. 
3. Layout dan Formatting:
    indentasi dari kode mengikuti standar dari java dan formattingnya vertical, saya juga menggunakan blank line untuk memisahkan antar concept (contoh: antara method dan deklarasi field)

Secure Coding practices yang telah diterapkan
1. UUID untuk identification:
    menggunakan UUID untuk id produk karena UUID itu lebih sulit jika ada penyerang yang ingin nebak idnya apa
2. Output data encoding: 
    karena kita menggunakan Thymeleaf, secara implisit kita mengaplikasikan output encoding karena thymeleaf secara otomatis membebaskan kita dari special character yang membantu untuk prevensi Cross-Site Scripting (XSS)

room of improvement untuk kode:
dalam method findbyid di productrepository.java ia me return null kalo productnya not found
dalam modul di state bahwa kita jangan sampe return null

ruang improvement yang saya temukan, nanti kedepannya mungkin bisa menggunakan Optional.empty() untuk menggantikan return null

# Refleksi 2
1. setelah membuat unit test saya awalnya saya merasa bahwa cukup beberpa saja yang perlu kita implementasikan dalam membuat unit test, namun jika kita berlandaskan melalui code coverage cukup banyak hal yang perlu kita uji dalam membuat unit test, namun hal ini tentu tidak menjadikan kode kita mempunyai bug atau error bisa jadi terdapat beberapa logika yang kita dapat miss tapi tidak tercover dalam unit test karena dari kodenya sudah salah
2. menurut saya terkait cleanliness of the code pada functional test yang baru akan terdapat beberapa kode yang munklin terduplikasi karena hampir isinya sama, 
kemudian kita juga bisa jadi hanya memiliki sedikit helper method yang bisa di reuse, untuk maintenancenya pun akan susah karena bisa jadi developer baru akan kebingungan karena kesulitan dalam memahami tujuan dari test yang sebenarnya karena banyak yang duplikat. hal ini akhirnya tentu dapat mengurangi kualitas kodenya karena tidak sepenuhnya memenuhi prinsip clean code. 

supaya hal tersebut dapat kita hindari kita bisa menggunakan beberapa cara berikut
1. menghilangkan setup yang sama dengan base test class
2. membuat konfigurasinya menjadi centralized
3. helper utilities

3 hal ini dapat membuat yang awalnya bisa jadi sulit untuk di maintain dan not clean menjadi bersih, mudah dibaca, serta mudah untuk di maintain

