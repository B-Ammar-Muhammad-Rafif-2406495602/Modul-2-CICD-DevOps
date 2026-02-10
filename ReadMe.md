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
